package top.vuhe.admin.system.controller

import io.swagger.v3.oas.annotations.tags.Tag
import org.slf4j.LoggerFactory
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.servlet.ModelAndView
import top.vuhe.admin.api.constant.API_SYSTEM_PREFIX
import top.vuhe.admin.api.constant.COMMA
import top.vuhe.admin.api.exception.BusinessException
import top.vuhe.admin.spring.web.controller.BaseController
import top.vuhe.admin.spring.web.request.PageDomain
import top.vuhe.admin.spring.web.response.ResultObj
import top.vuhe.admin.system.service.ISysFileService
import javax.servlet.http.HttpServletResponse

/**
 * 文件控制器
 *
 * @author vuhe
 */
@RestController
@Tag(name = "资源文件")
@RequestMapping(API_SYSTEM_PREFIX + "file")
class SysFileController(
    private val fileService: ISysFileService
) : BaseController() {
    private val log = LoggerFactory.getLogger(this::class.java)

    /**
     * 文件管理页面
     */
    @GetMapping("main")
    @PreAuthorize("hasPermission('/system/file/main','sys:file:main')")
    fun main() = ModelAndView("system/file/main")

    /**
     * 文件上传视图
     */
    @GetMapping("add")
    @PreAuthorize("hasPermission('/system/file/add','sys:file:add')")
    fun add() = ModelAndView("system/file/add")

    /* -------------------------------------------------------------------------- */

    /**
     * 文件资源数据
     */
    @GetMapping("data")
    @PreAuthorize("hasPermission('/system/file/data','sys:file:data')")
    fun data(pageDomain: PageDomain) = pageTable {
        fileService.data(pageDomain)
    }

    /**
     * 文件上传接口
     */
    @PostMapping("upload")
    fun upload(@RequestParam("file") file: MultipartFile): ResultObj<String> {
        val result = fileService.upload(file)
        return if (result.isNotBlank()) {
            ResultObj.Success(0, "上传成功", result)
        } else {
            ResultObj.Fail(message = "上传失败")
        }
    }

    /**
     * 文件获取接口
     */
    @GetMapping("download/{id}")
    fun download(
        @PathVariable("id") id: String, response: HttpServletResponse
    ) = try {
        fileService.download(id, response)
    } catch (e: BusinessException) {
        log.warn("文件下载出错，内容为空！原因：${e.message}")
    }

    /**
     * 文件删除接口
     */
    @DeleteMapping("remove/{id}")
    @PreAuthorize("hasPermission('/system/file/remove','sys:file:remove')")
    fun remove(@PathVariable("id") id: String) = boolResult("删除成功", "删除失败") {
        fileService.remove(id)
    }

    /**
     * 文件删除接口
     */
    @Transactional(rollbackFor = [Exception::class])
    @DeleteMapping("batchRemove/{ids}")
    @PreAuthorize("hasPermission('/system/file/remove','sys:file:remove')")
    fun batchRemove(@PathVariable("ids") ids: String): ResultObj<*> {
        ids.split(COMMA).forEach { fileService.remove(it) }
        return ResultObj.Success<Nothing>(message = "删除成功")
    }
}
