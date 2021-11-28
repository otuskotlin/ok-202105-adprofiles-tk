package controllers

import backend.transport.mapping.kmp.toUpdateResponse
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import context.MpContext
import io.ktor.http.cio.websocket.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.ClosedReceiveChannelException
import kotlinx.coroutines.withContext
import models.IUserSession
import ru.tk.adprofiles.openapi.models.BaseMessage
import ru.tk.adprofiles.openapi.models.InitProfileRequest
import services.ProfileService
import java.time.Instant

// Это расширение делает вызов потенциально блокирующего метода writeValueAsString безопасным для корутин
suspend fun ObjectMapper.safeWriteValueAsString(value: Any): String =
    withContext(Dispatchers.IO) { writeValueAsString(value) }

class KtorUserSession(
    override val fwSession: WebSocketSession,
    private val objectMapper: ObjectMapper,
) : IUserSession<WebSocketSession> {
    override suspend fun notifyProfileChanged(context: MpContext) {
        val event = context.toUpdateResponse()
        fwSession.send(Frame.Text(objectMapper.safeWriteValueAsString(event)))
    }
}

suspend fun WebSocketSession.handleSession(
    objectMapper: ObjectMapper,
    profileService: ProfileService,
    userSessions: MutableSet<KtorUserSession>
) {
    val userSession = KtorUserSession(this, objectMapper)
    userSessions.add(userSession)

    try {
        run {
            // Обработка события init сессии
            serveRequest(InitProfileRequest(), userSession, profileService)?.also {
                outgoing.send(Frame.Text(objectMapper.safeWriteValueAsString(it)))
            }
        }

        for (frame in incoming) {
            if (frame is Frame.Text) {
                // Обработка события message сессии
                val request = objectMapper.readValue<BaseMessage>(frame.readText())
                serveRequest(request, userSession, profileService)?.also {
                    outgoing.send(Frame.Text(objectMapper.safeWriteValueAsString(it)))
                }
            }
        }
    } catch (_: ClosedReceiveChannelException) {
        // Веб-сокет закрыт по инициативе клиента
    } finally {
        userSessions.remove(userSession)
    }

    // Обработка события finished сессии
    serveRequest(null, userSession, profileService)
}

suspend fun serveRequest(request: BaseMessage?, userSession: KtorUserSession, profileService: ProfileService): BaseMessage? {
    val context = MpContext(startTime = Instant.now(), userSession = userSession)
    return try {
        if (request != null) {
        profileService.handleProfile(context, request)
        } else {
            profileService.finishProfile(context)
            null
        }
    } catch (e: Exception) {
        profileService.errorProfile(context, e)
    }
}
