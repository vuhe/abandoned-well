package top.vuhe.admin.system.controller

import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.ModelAndView
import top.vuhe.admin.api.constant.API_SYSTEM_PREFIX
import top.vuhe.admin.api.constant.COMMA
import top.vuhe.admin.api.text.SqlTextFilter
import top.vuhe.admin.spring.web.controller.BaseController
import top.vuhe.admin.spring.web.request.PageDomain
import top.vuhe.admin.spring.web.response.ResultObj
import top.vuhe.admin.system.domain.SysDictData
import top.vuhe.admin.system.service.ISysDictDataService
import javax.servlet.http.HttpServletRequest

/**
 * 数据字典控制器
 *
 * @author vuhe
 */
@RestController
@Tag(name = "字典类型")
@RequestMapping(API_SYSTEM_PREFIX + "dictData")
class SysDictDataController(
    private val sysDictDataService: ISysDictDataService
) : BaseController() {

    /**
     * 数据字典列表视图
     */
    @GetMapping("main")
    @PreAuthorize("hasPermission('/system/dictData/main','sys:dictData:main')")
    fun main(typeCode: String) = ModelAndView("system/dict/data/main").apply {
        addObject("typeCode", typeCode)
    }

    /**
     * 数据字典类型新增视图
     */
    @GetMapping("add")
    @PreAuthorize("hasPermission('/system/dictData/add','sys:dictData:add')")
    fun add(typeCode: String) = ModelAndView("system/dict/data/add").apply {
        addObject("typeCode", typeCode)
    }

    /**
     * 数据字典类型修改视图
     */
    @GetMapping("edit")
    @PreAuthorize("hasPermission('/system/dictData/edit','sys:dictData:edit')")
    fun edit(dataId: String) = ModelAndView("system/dict/data/edit").apply {
        addObject("sysDictData", sysDictDataService.getOneById(dataId))
    }

    /* -------------------------------------------------------------------------- */

    /**
     * 数据字典列表数据
     */
    @GetMapping("data")
    @PreAuthorize("hasPermission('/system/dictData/data','sys:dictData:data')")
    fun data(sysDictData: SysDictData, pageDomain: PageDomain) = pageTable {
        sysDictDataService.page(sysDictData, pageDomain)
    }

    /**
     * 根据字典code获取数据字典列表数据
     */
    @GetMapping("selectByCode")
    fun selectByCode(typeCode: String): ResultObj<List<SysDictData>> {
        val list = sysDictDataService.selectByCode(typeCode)
        return ResultObj.Success(data = list)
    }

    /**
     * 获取字典数据
     */
    @GetMapping(value = ["/getDictItems/{dictCode}"])
    fun getDictItems(
        @PathVariable dictCode: String,
        @RequestParam(value = "sign", required = false) sign: String?,
    ): ResultObj<List<SysDictData>> {
        return try {
            val ls = if (dictCode.contains(COMMA)) {
                val params = dictCode.split(",")
                if (params.size < 3) {
                    return ResultObj.Fail(message = "字典Code格式不正确！")
                }
                val sqlInjCheck = listOf(params[0], params[1], params[2])
                SqlTextFilter.filterContent(sqlInjCheck)
                when (params.size) {
                    4 -> {
                        SqlTextFilter.specialFilterContent(params[3])
                        sysDictDataService.queryTableDictItemsByCodeAndFilter(
                            params[0], params[1], params[2], params[3]
                        )
                    }
                    3 -> {
                        sysDictDataService.queryTableDictItemsByCode(params[0], params[1], params[2])
                    }
                    else -> return ResultObj.Fail(message = "字典Code格式不正确！")
                }
            } else {
                sysDictDataService.selectByCode(dictCode)
            }
            ResultObj.Success(data = ls)
        } catch (e: Exception) {
            e.printStackTrace()
            ResultObj.Fail(message = "操作失败！")
        }
    }

    /**
     * 根据字典code加载字典text
     * Param: dictCode 字典code
     * Return: Result
     */
    @RequestMapping(value = ["/loadDictItem/{dictCode}"], method = [RequestMethod.GET])
    fun loadDictItem(
        @PathVariable dictCode: String,
        @RequestParam(name = "key") keys: String,
        @RequestParam(value = "sign", required = false) sign: String?,
        request: HttpServletRequest?
    ): ResultObj<List<SysDictData>> {
        return try {
            if (dictCode.contains(COMMA)) {
                val params = dictCode.split(COMMA)
                if (params.size != 3) {
                    return ResultObj.Fail(message = "字典Code格式不正确！")
                }
                val keyArray = keys.split(COMMA)
                val texts = sysDictDataService.queryTableDictByKeys(params[0], params[1], params[2], keyArray)
                ResultObj.Success(data = texts)
            } else {
                ResultObj.Fail(message = "字典Code格式不正确！")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            ResultObj.Fail(message = "操作失败！")
        }
    }

    /**
     * 新增字典类型接口
     */
    @PostMapping("save")
    @PreAuthorize("hasPermission('/system/dictData/add','sys:dictData:add')")
    fun save(@RequestBody sysDictData: SysDictData) = boolResult {
        sysDictDataService.add(sysDictData)
    }

    /**
     * 数据字典类型修改视图
     */
    @PutMapping("update")
    @PreAuthorize("hasPermission('/system/dictData/edit','sys:dictData:edit')")
    fun update(@RequestBody sysDictData: SysDictData) = boolResult {
        sysDictDataService.modify(sysDictData)
    }

    /**
     * 数据字典删除
     */
    @DeleteMapping("remove/{id}")
    @PreAuthorize("hasPermission('/system/dictData/remove','sys:dictData:remove')")
    fun remove(@PathVariable("id") id: String) = boolResult {
        sysDictDataService.remove(id)
    }

    /**
     * 根据 Id 启用字典
     */
    @PutMapping("enable")
    fun enable(@RequestBody sysDictData: SysDictData) = boolResult {
        sysDictData.enable = true
        sysDictDataService.modify(sysDictData)
    }

    /**
     * 根据 Id 禁用字典
     */
    @PutMapping("disable")
    fun disable(@RequestBody sysDictData: SysDictData) = boolResult {
        sysDictData.enable = false
        sysDictDataService.modify(sysDictData)
    }
}
