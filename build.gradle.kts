plugins {
    kotlin("jvm") apply false
}

group = "ru.tk.adprofiles"
version = "0.0.1"

subprojects{
    group = rootProject.group
    version = rootProject.version

    repositories {
        mavenCentral()
    }
}


