package top.vuhe.admin.api.jackson

import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import com.fasterxml.jackson.module.kotlin.KotlinFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule

object LanguageModule : SimpleModule() {
    private val kotlinModule = KotlinModule.Builder()
        .withReflectionCacheSize(512)
        .configure(KotlinFeature.NullToEmptyCollection, true)
        .configure(KotlinFeature.NullToEmptyMap, true)
        .configure(KotlinFeature.NullIsSameAsDefault, false)
        .configure(KotlinFeature.SingletonSupport, false)
        .configure(KotlinFeature.StrictNullChecks, false)
        .build()

    override fun getDependencies() = listOf(kotlinModule, Jdk8Module())
}
