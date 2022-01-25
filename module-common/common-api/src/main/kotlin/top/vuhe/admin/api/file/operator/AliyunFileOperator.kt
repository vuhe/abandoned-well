package top.vuhe.admin.api.file.operator

import com.aliyun.oss.OSS
import com.aliyun.oss.OSSClientBuilder
import com.aliyun.oss.model.CannedAccessControlList
import org.springframework.util.FileCopyUtils
import org.springframework.web.multipart.MultipartFile
import top.vuhe.admin.api.exception.BusinessException
import top.vuhe.admin.api.file.FileInfo
import top.vuhe.admin.api.file.FileOperatorApi
import top.vuhe.admin.api.file.info.AliyunFileInfo
import top.vuhe.admin.api.file.property.AliyunOssProperties
import java.io.ByteArrayInputStream
import javax.servlet.http.HttpServletResponse

/**
 * 阿里云文件操作的实现
 *
 * @author vuhe
 */
class AliyunFileOperator(
    private val aliyunOssProperties: AliyunOssProperties
) : FileOperatorApi {
    private val bucketName get() = aliyunOssProperties.bucketName

    /** 阿里云文件操作客户端 */
    private lateinit var ossClient: OSS

    override val fileInfos: List<FileInfo> = listOf(AliyunFileInfo(bucketName))

    override fun initClient() {
        val endpoint = aliyunOssProperties.endPoint
        val accessKeyId = aliyunOssProperties.accessKeyId
        val accessKeySecret = aliyunOssProperties.accessKeySecret

        // 创建OSSClient实例。
        ossClient = OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret)
        // 设置为私有读写，文件通过此应用获取并在 controller 中鉴权
        ossClient.setBucketAcl(bucketName, CannedAccessControlList.Private)
    }

    override fun destroyClient() {
        ossClient.shutdown()
    }

    override fun filePath(id: String): String {
        return "aliyunOSS:$bucketName:$id"
    }

    override fun isExisting(id: String): Boolean {
        return aliyunTry {
            ossClient.doesObjectExist(bucketName, id)
        }
    }

    override fun upload(id: String, file: MultipartFile) {
        aliyunTry {
            ByteArrayInputStream(file.bytes).use {
                ossClient.putObject(bucketName, id, it)
            }
        }
    }

    override fun download(id: String, response: HttpServletResponse) {
        return aliyunTry {
            val ossObject = ossClient.getObject(bucketName, id)
            FileCopyUtils.copy(ossObject.objectContent, response.outputStream)
        }
    }

    override fun delete(id: String) {
        ossClient.deleteObject(bucketName, id)
    }

    private fun <T> aliyunTry(block: () -> T): T {
        return try {
            block()
        } catch (e: Exception) {
            throw BusinessException("阿里云文件操作失败")
        }
    }
}
