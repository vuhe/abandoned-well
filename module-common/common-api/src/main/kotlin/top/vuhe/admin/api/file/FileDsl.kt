package top.vuhe.admin.api.file

import cn.hutool.core.io.FileUtil
import org.slf4j.LoggerFactory
import java.io.File

/**
 * 创建File对象，自动识别相对或绝对路径，
 * 相对路径将自动从ClassPath下寻找
 *
 * @param path 相对ClassPath的目录或者绝对路径目录
 * @return FileDsl
 */
fun file(path: String): FileDsl {
    return FileDsl(FileUtil.file(path))
}

/**
 * ## 用于指出文件的 dsl
 * 由于本类方法大部分都是过程，因此方法大部分都没有返回值
 *
 * @author vuhe
 */
class FileDsl(val file: File) {
    /**
     * 文件存在
     */
    val isExist = FileUtil.exist(file)

    /**
     * 不存在
     */
    val isNotExist = FileUtil.exist(file).not()

    /**
     * 如果文件「存在」时执行
     */
    fun ifExist(block: FileDsl.() -> Unit) {
        try {
            if (FileUtil.exist(file)) {
                this.block()
            }
        } catch (e: Exception) {
            log.warn("操作文件失败，原因：{}", e.message)
        }
    }

    /**
     * 如果文件「不存在」时执行
     */
    fun ifNotExist(block: FileDsl.() -> Unit) {
        try {
            if (!FileUtil.exist(file)) {
                this.block()
            }
        } catch (e: Exception) {
            log.warn("操作文件失败，原因：{}", e.message)
        }
    }

    /**
     * 读取文件所有数据
     *
     * 文件的长度不能超过 Integer.MAX_VALUE
     */
    fun readBytes(): ByteArray = try {
        FileUtil.readBytes(file)
    } catch (e: Exception) {
        log.warn("读取文件失败，原因：{}", e.message)
        ByteArray(0)
    }

    /**
     * 删除文件或者文件夹
     *
     * 注意：删除文件夹时不会判断文件夹是否为空，如果不空则递归删除子文件或文件夹
     *
     * 某个文件删除失败会终止删除操作
     */
    fun del() {
        try {
            FileUtil.del(file)
        } catch (e: Exception) {
            log.warn("删除文件失败，原因：{}", e.message)
        }
    }

    /**
     * 创建文件夹，会递归自动创建其不存在的父文件夹，如果存在直接返回
     */
    fun mkdirs() {
        try {
            FileUtil.mkdir(file)
        } catch (e: Exception) {
            log.warn("创建文件(夹)失败，原因：{}", e.message)
        }
    }

    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }
}
