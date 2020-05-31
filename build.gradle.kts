plugins {
    id("org.jetbrains.kotlin.js") version "1.3.70-eap-184"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    maven { setUrl("https://dl.bintray.com/kotlin/kotlin-eap") }
    maven("https://kotlin.bintray.com/kotlin-js-wrappers/")
    mavenCentral()
    jcenter()
}

dependencies {
    implementation(kotlin("stdlib-js"))

    //React, React DOM + Wrappers (chapter 3)
    implementation("org.jetbrains:kotlin-react:16.13.0-pre.94-kotlin-1.3.70")
    implementation("org.jetbrains:kotlin-react-dom:16.13.0-pre.94-kotlin-1.3.70")
    implementation(npm("react", "16.13.1"))
    implementation(npm("react-dom", "16.13.1"))
    //implementation(npm("@material-ui/core", "3.3.2"))
    //implementation(npm("@material-ui/icons", "3.0.1"))
    testImplementation(kotlin("test-js"))

    //Kotlin Styled (chapter 3)
    implementation("org.jetbrains:kotlin-styled:1.0.0-pre.94-kotlin-1.3.70")
    implementation(npm("styled-components"))
    implementation(npm("inline-style-prefixer"))

    //Video Player (chapter 7)
    implementation(npm("react-player"))

    //Share Buttons (chapter 7)
    implementation(npm("react-share"))

    //Coroutines (chapter 8)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-common:1.3.5")
}

tasks {
    compileKotlinJs {
        kotlinOptions.freeCompilerArgs = listOf("-Xopt-in=kotlin.time.ExperimentalTime")
    }
    compileTestKotlinJs {
        kotlinOptions.freeCompilerArgs = listOf("-Xopt-in=kotlin.time.ExperimentalTime")
    }
}

apply(from = "updateData.gradle.kts")

kotlin.target.browser {
//    testTask {
//        useKarma {
//            usePhantomJS()
//        }
//    }
}

buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        //"classpath"(group = "org.json", name = "json", version = "20190722")
        "classpath"(group = "javax.json", name = "javax.json-api", version = "1.1.4")
        "classpath"(group = "org.glassfish", name = "javax.json", version = "1.1.4")
        //"runtime"(group = "org.jetbrains.kotlinx", name = "kotlinx-serialization-runtime", version = "0.20.0-1.4-M1-release-99")
    }
}
