rootProject.name = "adprofiles"

pluginManagement{
    val kotlinVersion: String by settings
    val openApiVersion: String by settings
    val bmuschkoVersion: String by settings

    plugins {
        kotlin("jvm") version kotlinVersion
        id("org.openapi.generator") version openApiVersion

        id("com.bmuschko.docker-java-application") version bmuschkoVersion
    }
}


include("common")
include("adprofiles-transport-main-openapi")
include("adprofiles-tk-models")
include("adprofiles-tk-mapper")
include("adprofiles-tk-app-ktor")
include("adprofiles-tk-rabbit")
include("adprofiles-tk-common-cor")
include("adprofiles-tk-common")
include("adprofiles-tk-logics")
include("adprofiles-tk-repo-inmemory")
