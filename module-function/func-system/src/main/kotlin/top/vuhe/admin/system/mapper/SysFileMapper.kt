package top.vuhe.admin.system.mapper

import org.ktorm.dsl.batchInsert
import org.ktorm.dsl.insert
import org.ktorm.schema.datetime
import org.ktorm.schema.varchar
import org.springframework.stereotype.Repository
import top.vuhe.admin.spring.database.mapper.CurdMapper
import top.vuhe.admin.spring.security.principal.LoginUserInfo
import top.vuhe.admin.system.domain.SysFile
import java.time.LocalDateTime

/**
 * 文件服务接口
 *
 * @author vuhe
 */
@Repository
class SysFileMapper : CurdMapper<SysFile>("sys_file") {
    override val id = varchar("id").primaryKey().bind(SysFile::id)
    private val fileName = varchar("file_name").bind(SysFile::fileName)
    private val filePath = varchar("file_path").bind(SysFile::filePath)
    private val fileType = varchar("file_type").bind(SysFile::fileType)
    private val fileSize = varchar("file_size").bind(SysFile::fileSize)
    private val fileDesc = varchar("file_desc").bind(SysFile::fileDesc)
    private val targetDate = datetime("target_date").bind(SysFile::targetDate)

    private val createTime = datetime("create_time").bind(SysFile::createTime)
    private val createBy = varchar("create_by").bind(SysFile::createBy)
    private val updateTime = datetime("update_time").bind(SysFile::updateTime)
    private val updateBy = varchar("update_by").bind(SysFile::updateBy)
    private val remark = varchar("remark").bind(SysFile::remark)

    override fun insert(entity: SysFile): Int {
        entity.createTime = LocalDateTime.now()
        entity.createBy = LoginUserInfo.currUserId
        return database.insert(this) {
            // 为保持一致，此处不重复生成 id
            insertSetting(entity)
            set(id, entity.id)
        }
    }

    override fun batchInsert(entities: Collection<SysFile>): Int {
        return database.batchInsert(this) {
            entities.forEach { e ->
                e.createTime = LocalDateTime.now()
                e.createBy = LoginUserInfo.currUserId
                item {
                    // 为保持一致，此处不重复生成 id
                    insertSetting(e)
                    set(id, e.id)
                }
            }
        }.reduce { a, b -> a + b }
    }
}
