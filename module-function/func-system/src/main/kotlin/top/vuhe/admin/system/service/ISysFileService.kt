package top.vuhe.admin.system.service

import org.springframework.web.multipart.MultipartFile
import top.vuhe.admin.spring.database.service.TablePage
import top.vuhe.admin.spring.web.request.PageDomain
import top.vuhe.admin.system.domain.SysFile
import javax.servlet.http.HttpServletResponse

/**
 * 文件服务接口
 *
 * @author vuhe
 */
interface ISysFileService {
    /**
     * 文件上传服务
     */
    fun upload(file: MultipartFile): String

    /**
     * 文件下载服务
     */
    fun download(id: String, response: HttpServletResponse)

    /**
     * 文件列表
     */
    fun data(pageDomain: PageDomain): TablePage<SysFile>

    /**
     * 删除文件
     */
    fun remove(id: String): Boolean
}
