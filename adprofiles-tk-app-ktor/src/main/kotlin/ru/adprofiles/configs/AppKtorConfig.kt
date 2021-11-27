package configs

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import context.ContextConfig
import controllers.KtorUserSession
import io.ktor.application.*
import logics.ProfileCrud
import repo.IRepoProfile
import repo.inmemory.RepoProfileInMemory
import services.ProfileService
import java.time.Duration

data class AppKtorConfig constructor(
    val userSessions: MutableSet<KtorUserSession> = mutableSetOf(),
    val objectMapper: ObjectMapper = jacksonObjectMapper(),
    val profileRepoProd: IRepoProfile = RepoProfileInMemory(initObjects = listOf()),
    val profileRepoTest: IRepoProfile = RepoProfileInMemory(initObjects = listOf(), ttl = Duration.ofHours(1)),
    val contextConfig: ContextConfig = ContextConfig(
        repoProd = profileRepoProd,
        repoTest = profileRepoTest,
    ),
    val crud: ProfileCrud = ProfileCrud(contextConfig),
    val profileService: ProfileService = ProfileService(crud),
) {

    companion object {
    }
}
