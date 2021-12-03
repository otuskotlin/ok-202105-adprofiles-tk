rootProject.name = "adprofiles"

pluginManagement{

    plugins {
        val kotlinVersion: String by settings
        val openApiVersion: String by settings
        val bmuschkoVersion: String by settings

        kotlin("jvm") version kotlinVersion
        kotlin("multiplatform") version kotlinVersion
        kotlin("plugin.serialization") version kotlinVersion
        kotlin("plugin.allopen") version kotlinVersion
        id("org.openapi.generator") version openApiVersion
        id("com.bmuschko.docker-java-application") version bmuschkoVersion
    }
}


include("common")
include("adprofiles-tk-mappings")
include("adprofiles-tk-app-ktor")
include("adprofiles-tk-logics")
include("adprofiles-tk-stubs")
include("adprofiles-tk-validation")
include("adprofiles-tk-common-cor")
include("adprofiles-tk-repo-inmemory")
include("adprofiles-tk-repo-test")
include("adprofiles-tk-service-openapi")
include("adprofiles-tk-transport-openapi")
include("adprofiles-tk-common-cor")
include("adprofiles-tk-rabbit")
include("adprofiles-tk-storage-postgressql")
