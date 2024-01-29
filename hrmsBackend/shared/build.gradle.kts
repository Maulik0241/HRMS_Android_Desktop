plugins {
    kotlin("multiplatform")
    id("com.android.library")
}

kotlin {
    android {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
    jvm("desktop"){
        compilations.all {
            kotlinOptions.jvmTarget = "11"
        }
    }

//    listOf(
//        iosX64(),
//        iosArm64(),
//        iosSimulatorArm64()
//    ).forEach {
//        it.binaries.framework {
//            baseName = "shared"
//        }
//    }
     val ktor_version= "2.3.1"
    sourceSets {
        val commonMain by getting{
            dependencies {

                dependencies {
                    implementation("ch.qos.logback:logback-classic:1.2.6")
                    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-javafx:1.7.1")
                    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1")
                    implementation("io.ktor:ktor-client-core:$ktor_version")
                    implementation("io.ktor:ktor-client-cio:$ktor_version")
                }
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting
        val androidUnitTest by getting
//        val iosX64Main by getting
//        val iosArm64Main by getting
//        val iosSimulatorArm64Main by getting
//        val iosMain by creating {
//            dependsOn(commonMain)
//            iosX64Main.dependsOn(this)
//            iosArm64Main.dependsOn(this)
//            iosSimulatorArm64Main.dependsOn(this)
//        }
//        val iosX64Test by getting
//        val iosArm64Test by getting
//        val iosSimulatorArm64Test by getting
//        val iosTest by creating {
//            dependsOn(commonTest)
//            iosX64Test.dependsOn(this)
//            iosArm64Test.dependsOn(this)
//            iosSimulatorArm64Test.dependsOn(this)
//        }

        val desktopMain by getting{
            dependencies {
                implementation("ch.qos.logback:logback-classic:1.2.6")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-javafx:1.7.1")
                implementation("io.ktor:ktor-client-core:$ktor_version")
                implementation("io.ktor:ktor-client-cio:$ktor_version")
            }
        }

    }
}

android {
    namespace = "com.example.hrmsbackend"
    compileSdk = 33
    defaultConfig {
        minSdk = 24
    }
}