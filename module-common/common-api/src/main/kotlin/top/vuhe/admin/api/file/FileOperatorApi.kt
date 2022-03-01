package top.vuhe.admin.api.file

import org.springframework.web.multipart.MultipartFile
import javax.servlet.http.HttpServletResponse

/**
 * ### 文件操作
 * 此操作中的所有 id 均为文件名（含后缀）
 *
 * @author vuhe
 */
interface FileOperatorApi {

    /**
     * 初始化操作的客户端
     */
    fun initClient()

    /**
     * 销毁操作的客户端
     */
    fun destroyClient()

    /**
     * 返回保存文件的路径
     *
     * @param id 唯一标示id
     * @return 文件路径
     */
    fun filePath(id: String): String

    /**
     * 判断是否存在文件
     *
     * @param id 唯一标示id，例如a.txt, doc/a.txt
     * @return true-存在文件，false-不存在文件
     */
    fun isExisting(id: String): Boolean

    /**
     * 上传文件
     *
     * @param id   文件 id
     * @param file 文件信息
     */
    fun upload(id: String, file: MultipartFile)

    /**
     * 下载文件
     *
     * @param id       文件 id
     * @param response 文件下载到响应中
     */
    fun download(id: String, response: HttpServletResponse)

    /**
     * 删除文件
     *
     * @param id 文件唯一标识
     */
    fun delete(id: String)
}
