package features

import io.ktor.application.*
import io.ktor.routing.*
import io.ktor.websocket.*
import configs.AppKtorConfig
import controllers.handleSession

fun Application.wsFeature(config: AppKtorConfig) {
    val userSessions = config.userSessions
    val objectMapper = config.objectMapper
    val profileService = config.profileService
    install(WebSockets)
    routing {
        webSocket("ws") {
            this.handleSession(objectMapper, profileService, userSessions)
        }
    }
}
