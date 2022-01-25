package top.vuhe.admin.generator.domain

/**
 * 查询类型
 *
 * @author vuhe
 */
enum class QueryType(val funcName: String) {
    /** 等于 */
    EQ("eq"),

    /** 不等于 */
    NE("notEq"),

    /** 大于 */
    GT("greater"),

    /** 小于 */
    LT("less"),

    /** 模糊 */
    LIKE("like"),

    /** 范围 */
    BETWEEN("between");
}
