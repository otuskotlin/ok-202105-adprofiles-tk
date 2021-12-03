package features

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.SerializationFeature
import configs.AppKtorConfig
import controllers.createProfile
import controllers.deleteProfile
import controllers.readProfile
import controllers.updateProfile
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.jackson.*
import io.ktor.response.*
import io.ktor.routing.*

fun Application.restFeature(config: AppKtorConfig) {
    val profileService = config.profileService

    install(DefaultHeaders)
    install(CORS) {
        method(HttpMethod.Options)
        method(HttpMethod.Put)
        method(HttpMethod.Delete)
        method(HttpMethod.Patch)
        header("MyCustomHeader")
        allowCredentials = true
        anyHost() // TODO remove
    }
    install(ContentNegotiation) {
        jackson {
            disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)

            enable(SerializationFeature.INDENT_OUTPUT)
            writerWithDefaultPrettyPrinter()
        }
    }

    routing {
        get("/") {
            call.respondText("Hello, world!")
        }
        // routing profile
        route("profile") {
            post("create") {
                call.createProfile(profileService)
            }
            post("read") {
                call.readProfile(profileService)
            }
            post("update") {
                call.updateProfile(profileService)
            }
            post("delete") {
                call.deleteProfile(profileService)
            }
        }
        // Static feature. Try to access `/static/ktor-logo.png`
        static("static") {
            resources("static")
        }
    }
}
