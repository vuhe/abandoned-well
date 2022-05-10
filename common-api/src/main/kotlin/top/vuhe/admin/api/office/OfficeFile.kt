package top.vuhe.admin.api.office

import java.io.OutputStream

fun interface OfficeFile {
    fun writeTo(output: OutputStream)
}
