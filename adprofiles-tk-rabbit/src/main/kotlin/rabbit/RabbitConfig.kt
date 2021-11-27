package rabbit

import context.ContextConfig
import logics.ProfileCrud
import repo.inmemory.RepoProfileInMemory
import services.ProfileService
import java.time.Duration

class RabbitConfig(
    val host: String = HOST,
    val port: Int = PORT,
    val user: String = USER,
    val password: String = PASSWORD,
    val contextConfig: ContextConfig = ContextConfig(
        repoProd = RepoProfileInMemory(initObjects = listOf(), ttl = Duration.ofHours(1)),
        repoTest = RepoProfileInMemory(initObjects = listOf()),
    ),
    val crud: ProfileCrud = ProfileCrud(contextConfig),
    val service: ProfileService = ProfileService(crud),
) {
    companion object {
        const val HOST = "localhost"
        const val PORT = 5672
        const val USER = "guest"
        const val PASSWORD = "guest"
    }
}
