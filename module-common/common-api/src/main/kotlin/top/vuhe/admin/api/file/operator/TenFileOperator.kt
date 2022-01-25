package top.vuhe.admin.api.file.operator

import cn.hutool.core.thread.ExecutorBuilder
import com.qcloud.cos.COSClient
import com.qcloud.cos.ClientConfig
import com.qcloud.cos.auth.BasicCOSCredentials
import com.qcloud.cos.exception.CosServiceException
import com.qcloud.cos.model.CannedAccessControlList
import com.qcloud.cos.model.GetObjectRequest
import com.qcloud.cos.model.ObjectMetadata
import com.qcloud.cos.region.Region
import com.qcloud.cos.transfer.TransferManager
import com.qcloud.cos.transfer.TransferManagerConfiguration
import org.springframework.util.FileCopyUtils
import org.springframework.web.multipart.MultipartFile
import top.vuhe.admin.api.exception.BusinessException
import top.vuhe.admin.api.file.FileInfo
import top.vuhe.admin.api.file.FileOperatorApi
import top.vuhe.admin.api.file.info.TencentFileInfo
import top.vuhe.admin.api.file.property.TenCosProperties
import java.io.ByteArrayInputStream
import java.util.concurrent.ExecutorService
import javax.servlet.http.HttpServletResponse

/**
 * 腾讯云内网文件操作
 *
 * @author vuhe
 */
class TenFileOperator(
    private val tenCosProperties: TenCosProperties
) : FileOperatorApi {
    private lateinit var cosClient: COSClient
    private lateinit var transferManager: TransferManager
    private val bucketName get() = tenCosProperties.bucketName

    override val fileInfos: List<FileInfo> = listOf(TencentFileInfo(bucketName))

    override fun initClient() {
        // 1.初始化用户身份信息
        val secretId = tenCosProperties.secretId
        val secretKey = tenCosProperties.secretKey
        val cred = BasicCOSCredentials(secretId, secretKey)

        // 2.设置 bucket 的区域, COS 地域的简称请参照
        // https://cloud.tencent.com/document/product/436/6224
        val region = Region(tenCosProperties.regionId)
        val clientConfig = ClientConfig(region)

        // 3.生成 cos 客户端。
        cosClient = COSClient(cred, clientConfig)

        // 4.线程池大小，建议在客户端与 COS 网络充足（例如使用腾讯云的 CVM，同地域上传 COS）的情况下，
        // 设置成16或32即可，可较充分的利用网络资源
        // 对于使用公网传输且网络带宽质量不高的情况，建议减小该值，避免因网速过慢，造成请求超时。

        // ExecutorService threadPool = Executors.newFixedThreadPool(32);
        // 线程池不允许使用Executors去创建，而是通过ThreadPoolExecutor的方式，
        // 这样的处理方式让写的同学更加明确线程池的运行规则，规避资源耗尽的风险。
        val threadPool: ExecutorService = ExecutorBuilder.create().build()

        // 5.传入一个 thread pool, 若不传入线程池，默认 TransferManager 中会生成一个单线程的线程池。
        transferManager = TransferManager(cosClient, threadPool)

        // 6.设置高级接口的分块上传阈值和分块大小为10MB
        val transferManagerConfiguration = TransferManagerConfiguration()
        transferManagerConfiguration.multipartUploadThreshold = 10 * 1024 * 1024
        transferManagerConfiguration.minimumUploadPartSize = 10 * 1024 * 1024
        transferManager.configuration = transferManagerConfiguration

        cosClient.setBucketAcl(bucketName, CannedAccessControlList.Private)
    }

    override fun destroyClient() {
        cosClient.shutdown()
    }

    override fun filePath(id: String): String {
        return "tencentOSS:$bucketName$id"
    }

    override fun isExisting(id: String): Boolean {
        return try {
            cosClient.getObjectMetadata(bucketName, id)
            true
        } catch (e: CosServiceException) {
            false
        }
    }

    override fun upload(id: String, file: MultipartFile) {
        tencentTry {
            val objectMetadata = ObjectMetadata().apply {
                this.contentType = "application/octet-stream"
            }
            ByteArrayInputStream(file.bytes).use {
                cosClient.putObject(bucketName, id, it, objectMetadata)
            }
        }
    }

    override fun download(id: String, response: HttpServletResponse) {
        return tencentTry {
            val getObjectRequest = GetObjectRequest(bucketName, id)
            val cosObject = cosClient.getObject(getObjectRequest)
            FileCopyUtils.copy(cosObject.objectContent, response.outputStream)
        }
    }

    override fun delete(id: String) {
        cosClient.deleteObject(bucketName, id)
    }

    private fun <T> tencentTry(block: () -> T): T {
        return try {
            block()
        } catch (e: Exception) {
            throw BusinessException("腾讯云文件操作失败")
        }
    }
}
