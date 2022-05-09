package top.vuhe.admin.api.network

import java.nio.charset.Charset
import javax.servlet.ReadListener
import javax.servlet.ServletInputStream
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletRequestWrapper

/**
 * 可重复读 body 包装
 *
 * @author vuhe
 */
class RepeatableReadRequest(request: HttpServletRequest) : HttpServletRequestWrapper(request) {
    private val charset get() = Charset.forName(characterEncoding)
    private val body = request.inputStream.readBytes()

    override fun getInputStream(): ServletInputStream = BodyCachingStream(body)
    override fun getReader() = body.inputStream().bufferedReader(charset)

    private class BodyCachingStream(bytes: ByteArray) : ServletInputStream() {
        private val inputStream = bytes.inputStream()
        override fun read(): Int = inputStream.read()
        override fun isFinished(): Boolean = inputStream.available() == 0
        override fun isReady(): Boolean = true
        override fun setReadListener(readlistener: ReadListener) {}
    }
}
