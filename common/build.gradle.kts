
val coroutinesVersion: String by project

plugins {
    java
    kotlin("jvm")
}
dependencies {
    implementation(kotlin("stdlib"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")

    testImplementation(kotlin("test-junit"))
}
