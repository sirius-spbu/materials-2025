plugins {
    kotlin("jvm") version "2.0.21"
}

group = "com.explyt"
version = "1.0-SNAPSHOT"

repositories {
    maven(url = "https://jitpack.io")
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))

    implementation("com.github.UnitTestBot.kosat:kosat:f1c9848ac2")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}