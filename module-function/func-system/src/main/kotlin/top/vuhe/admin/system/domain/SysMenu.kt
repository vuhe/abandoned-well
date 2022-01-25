package top.vuhe.admin.system.domain

/**
 * 前端菜单数据封装信息
 *
 * @param id       菜单编号
 * @param parentId 父节点
 * @param title    标题
 * @param type     菜单类型
 * @param openType 打开类型
 * @param icon     图标
 * @param href     跳转路径
 * @param children 子菜单
 * @param username 用于参数传递
 * @author vuhe
 */
data class SysMenu(
    val id: String,
    val parentId: String,
    val title: String,
    val type: String,
    val openType: String,
    val icon: String,
    val href: String,
    val username: String,
    var children: List<SysMenu> = emptyList()
)
