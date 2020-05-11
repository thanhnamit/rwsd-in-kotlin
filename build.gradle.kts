plugins {
    kotlin("jvm") version "1.3.72"
}

group = "com.tna"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
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