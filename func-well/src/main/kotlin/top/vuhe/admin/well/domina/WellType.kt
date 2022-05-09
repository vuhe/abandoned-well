package top.vuhe.admin.well.domina

/**
 * 废弃井类型
 *
 * @author vuhe
 */
@Suppress("unused")
enum class WellType(val category: String, val type: String) {
    KU("矿井", "竖井"),
    KX("矿井", "斜井"),
    KP("矿井", "平硐"),
    KO("矿井", "其他"),

    ZU("钻井", "石油井"),
    ZT("钻井", "天然气井"),
    ZW("钻井", "瓦斯抽采与排放井"),
    ZD("钻井", "地热井"),
    ZL("钻井", "卤水井"),
    ZP("钻井", "普通地质钻孔"),
    ZK("钻井", "水文孔"),
    ZO("钻井", "其他"),

    QG("取水井", "供水井"),
    QD("取水井", "地下水监测井"),
    QK("取水井", "水文地质勘探井"),
    QU("取水井", "疏降水井"),
    QN("取水井", "农田灌溉井"),
    QO("取水井", "其他");

    /** 井类型字符串 */
    val tag: String = "$category-$type"
}
