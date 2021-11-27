package controllers

import configs.AppKtorConfig
import io.ktor.http.cio.websocket.*
import io.ktor.server.testing.*
import ru.otus.otuskotlin.marketplace.module
import ru.tk.adprofiles.openapi.models.CreateProfileRequest
import ru.tk.adprofiles.openapi.models.CreateProfileResponse
import ru.tk.adprofiles.openapi.models.InitProfileResponse
import test.ktor.utils.Utils
import kotlin.test.Test
import kotlin.test.assertIs

class WsControllerTest {
    @Test
    fun test() {
        withTestApplication({
            module(config = AppKtorConfig())
        }) {
            handleWebSocketConversation("/ws") { incoming, outgoing ->
                run {
                    val responseFrame = incoming.receive()
                    assertIs<InitProfileResponse>(response)
                }

                run {
                    val request = CreateProfileRequest(
                        createProfile = Utils.stubCreatableProfile,
                        debug = Utils.stubSuccessDebug
                    )
                    val requestFrame = Frame.Text(Utils.mapper.writeValueAsString(request))
                    outgoing.send(requestFrame)

                    val responseFrame = incoming.receive()
                    assertIs<CreateProfileResponse>(response)
                }
            }
        }
    }
}
