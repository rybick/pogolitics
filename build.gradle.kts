import org.jetbrains.kotlin.gradle.targets.js.yarn.yarn

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

//rootProject.plugins.withType<org.jetbrains.kotlin.gradle.targets.js.yarn.YarnPlugin> {
//    // rootProject.the<org.jetbrains.kotlin.gradle.targets.js.yarn.YarnRootExtension>().download = false
//}

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.jetbrains.kotlin-wrappers:kotlin-react-legacy:17.0.2-${kotlinJS}")
    implementation("org.jetbrains.kotlin-wrappers:kotlin-react-dom-legacy:17.0.2-${kotlinJS}")
    implementation("org.jetbrains.kotlin-wrappers:kotlin-react-router-dom:6.2.1-${kotlinJS}")
    implementation("org.jetbrains.kotlin-wrappers:kotlin-styled:5.3.3-${kotlinJS}")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0")

//    implementation(npm("babel-cli", "6"))
//    implementation(npm("babel-preset-react-app", "3"))
//    implementation(npm("babel-core", "6.26.3"))
//    implementation(npm("babel-loader", "7.1.5"))
    // native
//    implementation(npm("react-native-switch-toggle", "2.2.1"))
    implementation(npm("react-player", "2.10.1"))
    implementation(npm("bootstrap-switch-button-react", "1.2.0"))

    compileOnly(npm("babel-loader", "9.1.0"))
    compileOnly(npm("@babel/core", "7.20.7"))
    compileOnly(npm("@babel/preset-env", "7.20.2"))
    compileOnly(npm("@babel/preset-react", "7.18.6"))
    compileOnly(npm("@babel/preset-flow", "7.18.6"))
//    compileOnly(npm("react-native", "0.70.6"))
}

kotlin {
    js(LEGACY) {
        binaries.executable()
        browser {
            commonWebpackConfig {
                cssSupport.enabled = true
            }
        }
        useCommonJs()
    }
}

// dirty hack
// also TODO
//tasks.register("enrichWebpack") {
//    //outputs.dir("inputs")
//    doLast {
//        file("build/js/packages/pogolitics/webpack.config.js")
//            .appendText("""
//                config.module.rules.push({
//                    test: /\.js${'$'}/,
//                    use: ["babel-loader"]
//                });
//            """.trimIndent())
//    }
//}

//tasks["browserProductionWebpack"].dependsOn(tasks["enrichWebpack"])
//tasks["enrichWebpack"].dependsOn(tasks["processDceKotlinJs"])

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