package top.vuhe.admin.api.monitor

import cn.hutool.system.oshi.OshiUtil
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import java.util.concurrent.ScheduledThreadPoolExecutor
import java.util.concurrent.TimeUnit

/**
 * ## 刷新 Cpu 信息
 */
@Component
class CpuInfoUpdater : CommandLineRunner {
    override fun run(vararg args: String?) {
        ScheduledThreadPoolExecutor(1) { r ->
            Thread(r).apply {
                name = "cpuInfoUpdater"
                isDaemon = true
            }
        }.scheduleWithFixedDelay({
            val info = OshiUtil.getCpuInfo(5000)
            CpuInfo.updateInfo(info)
        }, 0, 60, TimeUnit.SECONDS)
    }
}
