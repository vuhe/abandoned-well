package top.vuhe.admin.system.domain

import top.vuhe.admin.spring.database.entity.BaseEntity
import java.time.LocalDateTime

/**
 * 文件接口实体
 *
 * @author vuhe
 */
class SysFile : BaseEntity() {
    /**
     * 文件编号
     */
    override var id: String = ""

    /**
     * 文件名称
     */
    var fileName: String = ""

    /**
     * 文件路径
     */
    var filePath: String = ""

    /**
     * 文件类型
     */
    var fileType: String = ""

    /**
     * 文件大小
     */
    var fileSize: String = ""

    /**
     * 文件描述
     */
    var fileDesc: String = ""

    /**
     * 所属日期
     */
    var targetDate: LocalDateTime? = null
}
