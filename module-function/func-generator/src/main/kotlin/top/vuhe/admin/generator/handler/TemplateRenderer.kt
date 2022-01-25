package top.vuhe.admin.generator.handler

import org.apache.commons.lang3.StringUtils
import org.apache.velocity.VelocityContext
import org.apache.velocity.app.Velocity
import top.vuhe.admin.generator.domain.GenTable
import top.vuhe.admin.spring.database.entity.column.IdMaker
import java.io.StringWriter

/**
 * 模版渲染器
 *
 * @author vuhe
 */
object TemplateRenderer {
    /** 项目空间路径  */
    private const val PROJECT_PATH = "main/kotlin"

    /** html空间路径  */
    private const val TEMPLATES_PATH = "main/resources/templates"

    /**
     * 渲染模版，并调用输出函数
     * `(templateName, templateString) -> Unit`
     */
    fun render(table: GenTable, block: (String, String) -> Unit) {
        val context: VelocityContext = makeTemplateContext(table)
        val templates = makeTemplateList(table.tplCategory)
        templates.forEach { name ->
            // 默认 UTF-8 渲染
            val tpl = Velocity.getTemplate(name)
            val template = StringWriter().use {
                tpl.merge(context, it)
                it.toString()
            }
            block(name, template)
        }
    }

    /**
     * 生成全路径文件名称
     */
    fun makeFullPathFileName(table: GenTable, template: String): String {
        val packageName: String = table.packageName
        val moduleName: String = table.moduleName
        val className: String = table.className
        val businessName: String = table.businessName

        val javaPath: String = PROJECT_PATH + "/" + packageName.replace(".", "/")
        val htmlPath = "$TEMPLATES_PATH/$moduleName/$businessName"

        return when {
            "domain.kt" in template -> "${javaPath}/domain/${className}.kt"
            "sub-domain.kt" in template && "sub" == table.tplCategory ->
                "${javaPath}/domain/${table.subTable!!.className}.kt"
            "mapper.kt" in template -> "${javaPath}/mapper/${className}Mapper.kt"
            "service.kt" in template -> "${javaPath}/service/I${className}Service.kt"
            "serviceImpl.kt" in template -> "${javaPath}/service/impl/${className}ServiceImpl.kt"
            "controller.kt" in template -> "${javaPath}/controller/${className}Controller.kt"

            "list.html" in template -> "${htmlPath}/main.html"
            "tree.html" in template -> "${htmlPath}/main.html"
            "add.html" in template -> "${htmlPath}/add.html"
            "edit.html" in template -> "${htmlPath}/edit.html"

            "sql.vm" in template -> "${businessName}Menu.sql"

            else -> ""
        }
    }

    /**
     * 生成模版 Context
     */
    private fun makeTemplateContext(table: GenTable): VelocityContext {
        val moduleName: String = table.moduleName
        val businessName: String = table.businessName
        val packageName: String = table.packageName
        val tplCategory: String = table.tplCategory
        val functionName: String = table.functionName
        val ids: List<String> = IdMaker.map { it.toString() }.take(10).toList()
        return VelocityContext().apply {
            put("tplCategory", table.tplCategory)
            put("tableName", table.tableName)
            put("functionName", functionName.ifEmpty { "【请填写功能名称】" })
            put("ClassName", table.className)
            put("className", table.className.uncapitalize())
            put("moduleName", table.moduleName)
            put("businessName", table.businessName)
            put("basePackage", packageName.getPackagePrefix())
            put("packageName", packageName)
            put("author", table.functionAuthor)
            put("pkColumn", table.pkColumn)
            put("importList", table.importList)
            put("permissionPrefix", "${moduleName}:${businessName}")
            put("columns", table.columns)
            put("table", table)
            put("ids", ids)
            // MenuVelocityContext
            val parentMenuId: String = table.parentMenuId
            put("parentMenuId", parentMenuId)
            // TreeVelocityContext
            if (tplCategory == "tree") {
                put("treeCode", table.treeCode)
                put("treeParentCode", table.treeParentCode)
            }
        }
    }

    /**
     * 文件模版的 class path 路径
     */
    private fun makeTemplateList(tplCategory: String): List<String> = buildList {
        add("vm/kotlin/domain.kt.vm")
        add("vm/kotlin/mapper.kt.vm")
        add("vm/kotlin/service.kt.vm")
        add("vm/kotlin/serviceImpl.kt.vm")
        add("vm/kotlin/controller.kt.vm")
        if ("crud" == tplCategory) {
            add("vm/html/list.html.vm")
        } else if ("tree" == tplCategory) {
            add("vm/html/tree.html.vm")
        }
        add("vm/html/add.html.vm")
        add("vm/html/edit.html.vm")
        add("vm/sql/sql.vm")
    }

    /**
     * 需要导入的类列表
     */
    private val GenTable.importList: Set<String>
        get() = columns.asSequence()
            .mapNotNull { it.columnType?.import }
            .filter { it.isNotEmpty() }
            .toSet()

    private fun String.uncapitalize() = StringUtils.uncapitalize(this)

    private fun String.getPackagePrefix(): String {
        val lastIndex: Int = lastIndexOf(".")
        return substring(0, lastIndex)
    }
}
