package top.vuhe.admin.spring.dsl

import org.springframework.boot.web.servlet.FilterRegistrationBean
import javax.servlet.Filter

fun <F : Filter> registrationFilter(name: String, filter: F) =
    FilterRegistrationBean<F>().also {
        it.filter = filter
        it.setName(name)
    }
