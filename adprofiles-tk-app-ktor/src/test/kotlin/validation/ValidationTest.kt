package validation

import configs.AppKtorConfig
import io.ktor.http.*
import io.ktor.server.testing.*
import org.junit.Test
import ru.otus.otuskotlin.marketplace.module
import ru.tk.adprofiles.openapi.models.CreateProfileResponse
import test.ktor.utils.Utils
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.fail

class ValidationTest {
    @Test
    fun `bad json`() {
        withTestApplication({
            module(config = AppKtorConfig())
        }) {
            handleRequest(HttpMethod.Post, "/profile/create") {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.withCharset(Charsets.UTF_8).toString())

                setBody("{")
            }.apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals(ContentType.Application.Json.withCharset(Charsets.UTF_8), response.contentType())
                val jsonString = response.content ?: fail("Null response json")
                println("|$jsonString|")

                val res = Utils.mapper.readValue(response.content, CreateProfileResponse::class.java)
                    ?: fail("Incorrect response format")

                assertEquals(CreateProfileResponse.Result.ERROR, res.result)
                assertTrue {
                    res.errors?.find { it.message?.lowercase()?.contains("unexpected end-of-input") == true } != null
                }

            }
        }
    }
}
