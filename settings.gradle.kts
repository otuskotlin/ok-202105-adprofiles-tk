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
include("adprofiles-transport-main-openapi")
