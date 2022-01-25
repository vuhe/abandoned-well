package top.vuhe.admin.api.office.handler

import cn.afterturn.easypoi.csv.CsvExportUtil
import cn.afterturn.easypoi.csv.CsvImportUtil
import cn.afterturn.easypoi.csv.entity.CsvExportParams
import cn.afterturn.easypoi.csv.entity.CsvImportParams
import cn.afterturn.easypoi.handler.inter.IReadHandler
import org.slf4j.LoggerFactory
import top.vuhe.admin.api.office.annotation.OfficeProperty
import top.vuhe.admin.api.office.enums.HandleMode
import java.io.InputStream
import java.io.OutputStream
import kotlin.reflect.KClass
import kotlin.reflect.full.memberProperties

/**
 * ### csv 导入导出
 * csv 的导入导出一般用于
 * 数据备份、数据恢复、批量整理
 *
 * @author vuhe
 */
internal object CsvDataHandler {
    private val log = LoggerFactory.getLogger(this::class.java)

    fun <T : Any> export(clazz: KClass<T>, list: List<T>, output: OutputStream) {
        // 获取过滤字段
        val exclusions = clazz.memberProperties.filter { property ->
            val annotation = property.annotations.find { it is OfficeProperty } as OfficeProperty?
            annotation != null && (annotation.ignore || annotation.mode == HandleMode.Import)
        }.map { it.name }.toTypedArray()

        // 设置过滤字段
        val params = CsvExportParams().also {
            it.exclusions = exclusions
        }

        // 输出
        try {
            output.use { CsvExportUtil.exportCsv(params, clazz.java, list, output) }
        } catch (e: Exception) {
            log.error("导出 csv 失败！", e)
        }
    }

    fun <T : Any> import(clazz: KClass<T>, inputStream: InputStream): List<T> {
        // 获取过滤字段，TODO 暂时不过滤
        val exclusions = emptyList<Nothing>()

        // 设置配置
        val list = ArrayList<T>()
        val handle = object : IReadHandler<T> {
            override fun handler(t: T) {
                list.add(t)
            }

            override fun doAfterAll() {
                log.debug("对象 ${clazz.simpleName} 解析完成，转换为 List")
            }
        }
        val params = CsvImportParams()

        // 读入
        try {
            CsvImportUtil.importCsv(inputStream, clazz.java, params, handle)
        } catch (e: Exception) {
            log.error("csv 文件解析错误！", e)
        }
        return list
    }
}
