plugins {
    kotlin("js") version "1.8.0"
}

val kotlinWrappersVersion = "1.0.0-pre.489"

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    maven("https://maven.pkg.jetbrains.space/kotlin/p/kotlin/kotlin-js-wrappers/")
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation(enforcedPlatform("org.jetbrains.kotlin-wrappers:kotlin-wrappers-bom:$kotlinWrappersVersion"))
    implementation("org.jetbrains.kotlin-wrappers:kotlin-react")
    implementation("org.jetbrains.kotlin-wrappers:kotlin-react-dom")
    implementation("org.jetbrains.kotlin-wrappers:kotlin-react-router-dom")
    implementation("org.jetbrains.kotlin-wrappers:kotlin-emotion")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0")

    implementation(npm("bootstrap-switch-button-react", "1.2.0"))
}

kotlin {
    js(IR) {
        binaries.executable()
        browser {
            commonWebpackConfig {
                cssSupport { enabled.set(true) }
            }
        }
    }
}

apply(from = "updateData.gradle.kts")

//tasks.getByName("processResources") {
//    doFirst {
//        val resourcesDir = sourceSets.get("main").output.resourcesDir
//        resourcesDir!!.mkdirs()
//        val contents = "<html><body>dupa hahaha</body></html>"
//        File(resourcesDir, "index.html").writeText(contents)
//    }
//}
//
//tasks["build"].dependsOn(tasks["processResources"])



tasks.register("generateIndexHtml") {
    doFirst {
        val indexHtmlParts = project.fileTree("${project.rootDir}/src/main/resources/index-html-d")
        val processedIndexDir = "${project.rootDir}/build/processedResources/js/main/"
        val concatenatedContents = indexHtmlParts
            .sortedBy { it.name }
            .joinToString("\n") { it.readText() }
        File(processedIndexDir, "index.html").writeText(concatenatedContents)
    }
}

tasks["packageJson"].dependsOn(tasks["generateIndexHtml"])
tasks["generateIndexHtml"].dependsOn(tasks["processResources"])

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