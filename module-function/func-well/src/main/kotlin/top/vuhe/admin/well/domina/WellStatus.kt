package top.vuhe.admin.well.domina

/**
 * 废弃井信息状态
 *
 * @author vuhe
 */
enum class WellStatus {
    /** 已上报（审核中）*/
    Reported,
    /** 审核不通过（打回修改）*/
    NotAccepted,
    /** 审核通过 */
    Approved
}
