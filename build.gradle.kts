import org.jetbrains.dokka.gradle.DokkaTask

plugins {
    kotlin("jvm") version "1.3.72"
    id("org.jetbrains.dokka") version "0.10.1"
}

group = "com.tna"
version = "1.0-SNAPSHOT"

repositories {
    jcenter()
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("com.beust:klaxon:5.2")
    implementation("no.tornado:tornadofx:1.7.20")
    implementation("org.bouncycastle:bcprov-jdk15on:1.65")
    implementation("org.java-websocket:Java-WebSocket:1.3.0")
    implementation("org.eclipse.jetty:jetty-server:9.4.28.v20200408")
    implementation("org.eclipse.jetty:jetty-servlet:9.4.28.v20200408")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.7")
    testImplementation("org.junit.jupiter:junit-jupiter:5.6.2")
    testImplementation("io.mockk:mockk:1.10.0")
    testImplementation("io.kotest:kotest-runner-junit5-jvm:4.0.5") // for kotest framework
    testImplementation("io.kotest:kotest-assertions-core-jvm:4.0.5") // for kotest core jvm assertions
    testImplementation("io.kotest:kotest-property-jvm:4.0.5") // for kotest property test
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}

tasks {
    val dokka by getting(DokkaTask::class) {
        outputFormat = "html"
        outputDirectory = "$buildDir/dokka"
    }
}