plugins {
    base
    kotlin("jvm") version "2.1.10"
    kotlin("plugin.serialization") version "2.1.10"
    `maven-publish`

    // Linter
    id("org.jmailen.kotlinter") version "5.0.1"
}

group = "sh.nemo"
version = "0.5.1"

repositories {
    mavenCentral()
}

dependencies {
    api(platform("io.ktor:ktor-bom:3.1.2"))

    api("io.ktor:ktor-client-core")
    api("io.ktor:ktor-client-cio")
    api("io.ktor:ktor-client-content-negotiation")
    api("io.ktor:ktor-serialization-kotlinx-json")
    api("io.ktor:ktor-client-logging")

    api("org.jetbrains.kotlinx:kotlinx-datetime:0.6.2")
    api("ch.qos.logback:logback-classic:1.5.12")

    testImplementation(kotlin("test"))
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.9.0")
    testImplementation("org.junit.jupiter:junit-jupiter:5.11.1")
    testImplementation("org.testcontainers:testcontainers:1.20.4")
    testImplementation("org.testcontainers:junit-jupiter:1.20.4")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType(org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask::class).all {
    compilerOptions.freeCompilerArgs.add("-opt-in=kotlin.RequiresOptIn")

}

val sourcesJar by tasks.registering(Jar::class) {
    archiveClassifier.set("sources")
    from(sourceSets["main"].allSource)
}

publishing {
    publications {
        register("mavenJava", MavenPublication::class) {
            from(components["java"])
            artifact(sourcesJar.get())
            groupId = project.group.toString()
            artifactId = project.name
            version = project.version.toString()
        }
    }
}
