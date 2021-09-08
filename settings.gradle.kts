rootProject.name = "adprofiles"

pluginManagement{
    val kotlinVersion: String by settings
    val openApiVersion: String by settings

    plugins {
        kotlin("jvm") version kotlinVersion
        id("org.openapi.generator") version openApiVersion
    }
}


include("common")
include("adprofiles-transport-main-openapi")
include("adprofiles-tk-models")
include("adprofiles-tk-mapper")
