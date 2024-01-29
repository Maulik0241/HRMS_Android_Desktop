import org.jetbrains.compose.desktop.application.dsl.TargetFormat


plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose") version "1.3.1"
    kotlin("plugin.serialization").version("1.8.0")

}



group = "com.example.hrmsbackend"
version = "1.0.0"



kotlin {
    jvm {
        withJava()
        compilations.all {
            kotlinOptions.jvmTarget = "11"
        }
    }
    val ktor_version = "2.3.1"
    sourceSets {
        val jvmMain by getting {
            kotlin.srcDirs("src/jvmMain/kotlin")
            dependencies {
                implementation(compose.desktop.currentOs)
                api(compose.runtime)
                api(compose.foundation)
                api(compose.material)
                api(compose.ui)
                api(compose.materialIconsExtended)

                implementation("ch.qos.logback:logback-classic:1.2.6")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-javafx:1.7.1")
                implementation("io.ktor:ktor-client-core:$ktor_version")
                implementation("io.ktor:ktor-client-cio:$ktor_version")
                implementation("io.ktor:ktor-client-core:$ktor_version")
                implementation("io.ktor:ktor-client-cio:$ktor_version")
                implementation("io.ktor:ktor-client-content-negotiation:$ktor_version")
                implementation("io.ktor:ktor-serialization-kotlinx-json:$ktor_version")
                implementation("io.ktor:ktor-client-logging:$ktor_version")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")
                implementation(project(":shared"))
            }
        }
    }
}



compose.desktop {
    application {
        mainClass = "MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "hrmsBackend"
        }
    }
}