package top.vuhe.admin.spring.dsl

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import top.vuhe.admin.api.annotation.ProjectDsl

internal inline fun openApiInfo(block: InfoDsl.() -> Unit): OpenAPI {
    return OpenAPI().apply { info = InfoDsl().apply(block).build() }
}

@ProjectDsl
class InfoDsl {
    var title: String = ""
    var description: String = ""
    var version: String = ""
    var termsOfService: String = ""
    val contact = Contact()
    val license = License()

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
