package top.vuhe.admin.api.file

import cn.hutool.core.io.FileUtil
import org.springframework.util.FileCopyUtils
import org.springframework.web.multipart.MultipartFile
import oshi.PlatformEnum
import oshi.SystemInfo
import java.io.File
import java.io.FileInputStream
import javax.servlet.http.HttpServletResponse

/**
 * 本地文件的操作
 *
 * @author vuhe
 */
object LocalFileOperator : FileOperatorApi {
    private var currentSavePath = ""
    private lateinit var workspace: File

    /** windows 系统文件上传路径 */
    private const val windowsPath: String = "D:\\tempFilePath"

    /** linux 系统文件上传路径 */
    private const val linuxPath: String = "/tmp/tempFilePath"

    /** mac 系统文件上传路径 */
    private const val macPath: String = "./tmp/tempFilePath"

    override fun initClient() {
        val path = when (SystemInfo.getCurrentPlatform()) {
            PlatformEnum.MACOS -> macPath
            PlatformEnum.WINDOWS -> windowsPath
            else -> linuxPath
        }

        workspace = FileUtil.mkdir(path)
        currentSavePath = path
    }

    override fun destroyClient() {
        // empty
    }

    override fun filePath(id: String): String {
        return "$currentSavePath${File.separator}$id"
    }

    override fun isExisting(id: String): Boolean {
        val file = FileUtil.file(workspace, id)
        return FileUtil.exist(file)
    }

    override fun upload(id: String, file: MultipartFile) {
        val path = FileUtil.file(workspace, id)
        // 如果有旧文件，会直接覆盖
        file.transferTo(path)
    }

    override fun download(id: String, response: HttpServletResponse) {
        val file = FileUtil.file(workspace, id)
        FileCopyUtils.copy(FileInputStream(file), response.outputStream)
    }

    override fun delete(id: String) {
        val file = FileUtil.file(workspace, id)
        FileUtil.del(file)
    }
}
