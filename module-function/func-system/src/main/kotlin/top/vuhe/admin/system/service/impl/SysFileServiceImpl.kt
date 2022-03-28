package top.vuhe.admin.system.service.impl

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import top.vuhe.admin.api.exception.BusinessException
import top.vuhe.admin.api.file.FileOperatorApi
import top.vuhe.admin.spring.database.entity.column.IdMaker
import top.vuhe.admin.spring.database.service.TablePage
import top.vuhe.admin.spring.web.request.PageDomain
import top.vuhe.admin.system.domain.SysFile
import top.vuhe.admin.system.mapper.SysFileMapper
import top.vuhe.admin.system.service.ISysFileService
import java.time.LocalDateTime
import javax.annotation.PostConstruct
import javax.annotation.PreDestroy
import javax.servlet.http.HttpServletResponse

/**
 * 文件服务接口实现
 *
 * @author vuhe
 */
@Service
class SysFileServiceImpl(
    private val fileOperator: FileOperatorApi,
    private val fileMapper: SysFileMapper
) : ISysFileService {
    /**
     * 初始化客户端
     */
    @PostConstruct
    fun init() {
        fileOperator.initClient()
    }

    /**
     * 销毁客户端
     */
    @PreDestroy
    fun close() {
        fileOperator.destroyClient()
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun upload(file: MultipartFile): String {
        val fileId: String = IdMaker.next().toString()
        val name = file.originalFilename ?: ""
        val suffixName = name.substring(name.lastIndexOf("."))

        fileOperator.upload(fileId + suffixName, file)

        val fileEntity = SysFile().apply {
            id = fileId
            fileName = fileId + suffixName
            filePath = fileOperator.filePath(id)
            fileType = suffixName.replace(".", "")
            fileSize = getPrintSize(file.size)
            fileDesc = name
            targetDate = LocalDateTime.now()
        }
        return if (fileMapper.insert(fileEntity) > 0) {
            fileId
        } else ""
    }

    override fun download(id: String, response: HttpServletResponse) {
        val file = fileMapper.selectById(id)
            ?: throw BusinessException("找不到对应文件信息！")
        fileOperator.download(file.fileName, response)
    }

    override fun data(pageDomain: PageDomain): TablePage<SysFile> {
        val list = fileMapper.selectList()
        return TablePage(list, pageDomain)
    }

    override fun remove(id: String): Boolean {
        val deleteResult = fileMapper.delete(id) > 0
        if (deleteResult) {
            fileOperator.delete(id)
        }
        return deleteResult
    }

    private fun getPrintSize(s: Long): String {
        // 如果字节数少于1024，则直接以B为单位，否则先除于1024，后3位因太少无意义
        var size = s
        if (size < UNIT) {
            return size.toString() + "B"
        } else {
            size /= UNIT
        }
        // 如果原字节数除于1024之后，少于1024，则可以直接以KB作为单位
        // 因为还没有到达要使用另一个单位的时候
        // 接下去以此类推
        if (size < UNIT) {
            return size.toString() + "KB"
        } else {
            size /= UNIT
        }
        return if (size < UNIT) {
            // 因为如果以MB为单位的话，要保留最后1位小数，
            // 因此，把此数乘以100之后再取余
            size *= CENTI
            "${size / CENTI}.${size % CENTI}MB"
        } else {
            // 否则如果要以GB为单位的，先除于1024再作同样的处理
            size = size * CENTI / UNIT
            "${size / CENTI}.${size % CENTI}GB"
        }
    }

    companion object {
        private const val UNIT = 1024
        private const val CENTI = 100
    }
}
