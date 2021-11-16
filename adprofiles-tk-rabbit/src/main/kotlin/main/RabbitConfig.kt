package main

import ru.adprofiles.service.AdService

class RabbitConfig(
    val host: String = HOST,
    val port: Int = PORT,
    val user: String = USER,
    val password: String = PASSWORD,
    val service: AdService = AdService(),
) {
    companion object {
        const val HOST = "localhost"
        const val PORT = 5672
        const val USER = "guest"
        const val PASSWORD = "guest"
    }
}
