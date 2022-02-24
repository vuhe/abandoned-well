package top.vuhe.admin.api.file

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
class LocalFileOperator(
    private val localFileProperties: LocalFileProperties
) : FileOperatorApi {
    private var currentSavePath = ""

    override val fileInfos: List<FileInfo>
        get() = SystemInfo().operatingSystem.fileSystem.fileStores
            .map { LocalFileInfo(it) }

    override fun initClient() {
        val path = when (SystemInfo.getCurrentPlatform()) {
            PlatformEnum.MACOS -> localFileProperties.macPath
            PlatformEnum.WINDOWS -> localFileProperties.windowsPath
            else -> localFileProperties.linuxPath
        }
        file(path).ifNotExist { mkdirs() }
        currentSavePath = path
    }

    override fun destroyClient() {
        // empty
    }

    override fun filePath(id: String): String {
        return "$currentSavePath${File.separator}$id"
    }

    override fun isExisting(id: String): Boolean {
        val path = "$currentSavePath${File.separator}$id"
        return file(path).isExist
    }

    override fun upload(id: String, file: MultipartFile) {
        val path = "$currentSavePath${File.separator}$id"
        // 如果有旧文件，会直接覆盖
        file.transferTo(file(path).file)
    }

    override fun download(id: String, response: HttpServletResponse) {
        val path = "$currentSavePath${File.separator}$id"
        file(path).ifExist {
            FileCopyUtils.copy(FileInputStream(file), response.outputStream)
        }
    }

    override fun delete(id: String) {
        val file = "$currentSavePath${File.separator}$id"
        file(file).ifExist { del() }
    }
}
