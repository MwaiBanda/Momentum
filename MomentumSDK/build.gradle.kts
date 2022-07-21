plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("com.android.library")
    kotlin("plugin.serialization")
    id( "com.squareup.sqldelight")

}

version = "1.0.0"

kotlin {
    android()
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    cocoapods {
        summary = "Some description for the Shared Module"
        homepage = "https://momentumindiana.org"
        ios.deploymentTarget = "14.1"
        podfile = project.file("../MomentumiOS/Podfile")
        framework {
            baseName = "MomentumSDK"
        }
    }
    
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("co.touchlab:stately-common:1.2.1")
                implementation("co.touchlab:stately-isolate:1.2.1")
                implementation("co.touchlab:stately-iso-collections:1.2.1")
                implementation("io.ktor:ktor-client-core:1.6.7")
                implementation("io.ktor:ktor-client-serialization:1.6.7")
                implementation("io.ktor:ktor-client-logging:1.6.7")
                implementation("io.insert-koin:koin-core:3.2.0")
                implementation("com.squareup.sqldelight:runtime:1.5.3")
                implementation("dev.gitlive:firebase-auth:1.6.1")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-okhttp:1.6.7")
                implementation("com.squareup.sqldelight:android-driver:1.5.3")

            }
        }
        val androidTest by getting
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
            dependencies {
                implementation("io.ktor:ktor-client-ios:1.6.7")
                implementation ("com.squareup.sqldelight:native-driver:1.5.3")

            }
        }
        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
        val iosTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }
    }

}

android {
    compileSdk = 32
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = 21
        targetSdk = 32
    }
}

sqldelight {
    database("MomentumDatabase") {
        packageName = "com.mwaibanda.momentum.data.db"
        sourceFolders = listOf("kotlin")
        linkSqlite = true
    }
}