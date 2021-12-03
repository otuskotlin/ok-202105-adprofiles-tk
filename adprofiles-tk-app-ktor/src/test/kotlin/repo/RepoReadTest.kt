package repo

import ProfileStub
import com.fasterxml.jackson.databind.ObjectMapper
import configs.AppKtorConfig
import io.ktor.http.*
import io.ktor.server.testing.*
import org.junit.Test
import repo.inmemory.RepoProfileInMemory
import ru.module
import ru.tk.adprofiles.openapi.models.BaseDebugRequest
import ru.tk.adprofiles.openapi.models.ProfileRole
import ru.tk.adprofiles.openapi.models.ReadProfileRequest
import ru.tk.adprofiles.openapi.models.ReadProfileResponse
import test.ktor.utils.Utils
import kotlin.test.assertEquals
import kotlin.test.fail

class RepoReadTest {
    @Test
    fun `read from db`() {
        val profileModel = ProfileStub.getModel()

        withTestApplication({
            val config = AppKtorConfig(
                profileRepoTest = RepoProfileInMemory(
                    initObjects = listOf(profileModel)
                )
            )
            module(config)
        }) {
            handleRequest(HttpMethod.Post, "/profile/read") {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.withCharset(Charsets.UTF_8).toString())

                val request = ReadProfileRequest(
                    readProfileId = profileModel.id.asString(),
                    debug = BaseDebugRequest(mode = BaseDebugRequest.Mode.TEST)
                )
                val json = ObjectMapper().writeValueAsString(request)
                setBody(json)
            }.apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals(ContentType.Application.Json.withCharset(Charsets.UTF_8), response.contentType())
                val jsonString = response.content ?: fail("Null response json")
                println("|$jsonString|")

                val res = Utils.mapper.readValue(response.content, ReadProfileResponse::class.java)
                    ?: fail("Incorrect response format")

                assertEquals(ReadProfileResponse.Result.SUCCESS, res.result)
                assertEquals(profileModel.id.asString(), res.readProfile?.id)
                assertEquals(ProfileRole.CUSTOMER, res.readProfile?.role)
                assertEquals(profileModel.firsName, res.readProfile?.firsName)
                assertEquals(profileModel.secondName, res.readProfile?.secondName)
            }
        }
    }
}
