package controllers

import configs.AppKtorConfig
import io.ktor.http.*
import io.ktor.server.testing.*
import ru.module
import ru.tk.adprofiles.openapi.models.BaseMessage
import test.ktor.utils.Utils
import kotlin.test.assertEquals

abstract class RouterTest {
    protected inline fun <reified T> testPostRequest(
        body: BaseMessage? = null,
        uri: String,
        config: AppKtorConfig = AppKtorConfig(),
        crossinline block: T.() -> Unit
    ) {
        withTestApplication({
            module(config = config)
        }) {
            handleRequest(HttpMethod.Post, uri) {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.withCharset(Charsets.UTF_8).toString())

                setBody(Utils.mapper.writeValueAsString(body))
            }.apply {
                println(response.content)
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals(ContentType.Application.Json.withCharset(Charsets.UTF_8), response.contentType())

                Utils.mapper.readValue(response.content, T::class.java).block()
            }
        }
    }
}
