plugins {
    kotlin("js") version "1.6.21"
}

val kotlinJS = "pre.290-kotlin-1.6.10"

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    maven("https://maven.pkg.jetbrains.space/kotlin/p/kotlin/kotlin-js-wrappers/")
    mavenCentral()
    jcenter()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.jetbrains.kotlin-wrappers:kotlin-react-legacy:17.0.2-${kotlinJS}")
    implementation("org.jetbrains.kotlin-wrappers:kotlin-react-dom-legacy:17.0.2-${kotlinJS}")
    implementation("org.jetbrains.kotlin-wrappers:kotlin-react-router-dom:6.2.1-${kotlinJS}")
    implementation("org.jetbrains.kotlin-wrappers:kotlin-styled:5.3.3-${kotlinJS}")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0")

    implementation(npm("bootstrap-switch-button-react", "1.2.0"))
}

kotlin {
    js(LEGACY) {
        binaries.executable()
        browser {
            commonWebpackConfig {
                cssSupport.enabled = true
            }
        }
    }
}

apply(from = "updateData.gradle.kts")

buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        "classpath"(group = "javax.json", name = "javax.json-api", version = "1.1.4")
        "classpath"(group = "org.glassfish", name = "javax.json", version = "1.1.4")
    }
}

rootProject.extensions.configure<org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootExtension> {
    versions.webpackCli.version = "4.10.0"
}