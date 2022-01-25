package top.vuhe.admin.spring.database.entity.column

/**
 * ### id 生成器
 *
 * @author vuhe
 */
object IdMaker : Sequence<Long>, Iterator<Long> {
    /*** 机 器 Id  */
    private const val WORKER_ID: Long = 0

    /*** 数 据 中 心  */
    private const val CENTER_ID: Long = 0

    /** 机 器 编 号 所 占 位 数  */
    private const val WORKER_ID_BITS = 5L

    /** 数 据 标 识 所 占 位 数  */
    private const val CENTER_ID_BITS = 5L

    /** 开 始 时 间 戳  */
    private const val POC = 1288834974657L

    /** 序 列 在 Id 中 所 占 的 位 数  */
    private const val SEQUENCE_BITS = 12L

    /** 为 算 法 提 供 可 用 配 置  */
    private const val WORKER_ID_SHIFT = SEQUENCE_BITS
    private const val MAX_WORKER_ID = (-1L shl WORKER_ID_BITS.toInt()).inv()
    private const val MAX_CENTER_ID = (-1L shl CENTER_ID_BITS.toInt()).inv()
    private const val CENTER_ID_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS
    private const val TIMESTAMP_LEFT_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS + CENTER_ID_BITS
    private const val SEQUENCE_MASK = (-1L shl SEQUENCE_BITS.toInt()).inv()

    /*** 毫 秒 内 序 列  */
    private var sequence = 0L

    /*** 上 次 Id 生 成 的 时 间 戳  */
    private var lastTimestamp = -1L

    /**
     * 此迭代器为无限迭代器
     */
    override fun hasNext(): Boolean = true

    /**
     * 获取下一个 Id
     */
    override fun next(): Long {
        try {
            return makeSequence()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return -1
    }

    override fun iterator(): Iterator<Long> = this

    @Synchronized
    fun makeSequence(): Long {
        var timestamp = System.currentTimeMillis()
        // 当前时间小于上次 Id 生成时间，说明系统时钟回退，应会抛出异常
        if (timestamp < lastTimestamp) {
            // 服务器时钟被调整了，Sequence 生成器停止服务
            throw Exception(
                String.format(
                    "Clock moved backwards.  Refusing to generate id for %d milliseconds",
                    lastTimestamp - timestamp
                )
            )
        }
        // 如果是同一时间生成，则进行毫秒内序列
        if (lastTimestamp == timestamp) {
            // 每次加 +
            sequence = sequence + 1 and SEQUENCE_MASK
            // 毫秒内序列溢出
            if (sequence == 0L) {
                // 阻塞到下一个毫秒,获取新的时间戳
                timestamp = tilNextMillis(lastTimestamp)
            }
        } else {
            sequence = 0L
        }
        // 暂存当前时间戳，为下次使用提供依据
        lastTimestamp = timestamp
        // 雪花算法核心
        return timestamp - POC shl TIMESTAMP_LEFT_SHIFT.toInt() or
                (CENTER_ID shl CENTER_ID_SHIFT.toInt()) or
                (WORKER_ID shl WORKER_ID_SHIFT.toInt()) or
                sequence
    }

    /**
     * 获取时间戳，如果当前时间小于记录时间，会等待
     */
    private fun tilNextMillis(lastTimestamp: Long): Long {
        var timestamp = System.currentTimeMillis()
        while (timestamp <= lastTimestamp) {
            timestamp = System.currentTimeMillis()
        }
        return timestamp
    }
}
