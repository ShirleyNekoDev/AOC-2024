plugins {
    kotlin("jvm") version "2.1.0"
    idea
}

dependencies {
    implementation(kotlin("stdlib"))

    testImplementation(platform("org.junit:junit-bom:5.11.3"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation(kotlin("test"))
}

tasks {
    wrapper {
        gradleVersion = "8.11.1"
    }

    kotlin {
        jvmToolchain(23)
    }

    test {
        jvmArgs = listOf("-ea")
        enableAssertions = true
        testLogging {
            showStandardStreams = true
        }
        useJUnitPlatform()
    }
}

idea {
    module {
        isDownloadJavadoc = true
        isDownloadSources = true
    }
}
