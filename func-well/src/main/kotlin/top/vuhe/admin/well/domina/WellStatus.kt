package top.vuhe.admin.well.domina

/**
 * ## 废弃井信息状态
 *
 * @param tag 井状态字符串
 * @author vuhe
 */
enum class WellStatus(val tag: String) {
    /** 已上报（审核中） */
    Reported("已上报（审核中）"),

    /** 审核不通过（打回修改） */
    NotAccepted("审核不通过（打回修改）"),

    /** 审核通过 */
    Approved("审核通过")
}
