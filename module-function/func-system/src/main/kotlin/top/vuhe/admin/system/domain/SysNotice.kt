package top.vuhe.admin.system.domain

import top.vuhe.admin.spring.database.entity.BaseEntity

/**
 * 系统通知
 *
 * @author vuhe
 */
class SysNotice : BaseEntity() {
    /** 编号  */
    override var id: String = ""

    /** 标题  */
    var title: String = ""

    /** 内容  */
    var content: String = ""

    /** 发送人  */
    var sender: String = ""

    /** 发送人  */
    var senderName: String = ""

    /** 接收者  */
    var accept: String = ""

    /** 接收人  */
    var acceptName: String = ""

    /** 类型  */
    var type: String = ""
}
