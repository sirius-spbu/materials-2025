plugins {
    kotlin("jvm") version "2.0.21"
}

group = "com.explyt"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    // core
    implementation("io.ksmt:ksmt-core:0.5.29")
    // z3 solver
    implementation("io.ksmt:ksmt-z3:0.5.29")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}