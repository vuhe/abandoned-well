package top.vuhe.office

import java.io.OutputStream

fun interface OfficeFile {
    fun writeTo(output: OutputStream)
}
