package ru

import configs.AppKtorConfig
import features.restFeature
import io.ktor.application.*
import io.ktor.routing.*

// function with config (application.conf)
fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("UNUSED_PARAMETER") // Referenced in application.conf
@JvmOverloads
fun Application.module(config: AppKtorConfig = AppKtorConfig()) {
    // Generally not needed as it is replaced by a `routing`
    install(Routing)

    restFeature(config)
}
