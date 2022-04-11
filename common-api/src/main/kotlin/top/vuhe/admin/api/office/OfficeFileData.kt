package top.vuhe.admin.api.office

import java.io.OutputStream

internal fun interface OfficeFileData {
    fun writeTo(output: OutputStream)
}
