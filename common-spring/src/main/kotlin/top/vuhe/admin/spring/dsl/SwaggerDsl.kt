package top.vuhe.admin.spring.dsl

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import top.vuhe.admin.api.annotation.ProjectDsl

internal inline fun openApi(block: ApiDsl.() -> Unit): OpenAPI {
    return ApiDsl().apply(block).build()
}

@ProjectDsl
internal class ApiDsl {
    internal val info = InfoDsl()

    inline fun info(block: InfoDsl.() -> Unit) = info.block()

    fun build() = OpenAPI().also {
        it.info = info.build()
    }
}

@ProjectDsl
internal class InfoDsl {
    var title: String = ""
    var description: String = ""
    var version: String = ""
    var termsOfService: String = ""
    internal val contact = Contact()
    internal val license = License()

    inline fun contact(block: Contact.() -> Unit) = contact.block()
    inline fun license(block: License.() -> Unit) = license.block()

    fun build() = Info().also {
        it.title = title
        it.description = description
        it.version = version
        it.termsOfService = termsOfService
        it.contact = contact
        it.license = license
    }
}
