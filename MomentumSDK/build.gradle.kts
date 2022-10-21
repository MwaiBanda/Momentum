plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("com.android.library")
    id( "com.squareup.sqldelight")
    kotlin("plugin.serialization") version "1.7.0"
}


version = "1.0.0"

kotlin {
    android()
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    cocoapods {
        summary = """
        The Momentum SDK facilitates seamlessly integration with auth, networking,
        database(local & cloud) & caching functionality for Android, iOS & iPadOS. 
        Copyright © 2022 Momentum. All rights reserved.
        """
        authors = "Mwai Banda"
        license = "MIT"
        homepage = "https://momentumindiana.org"
        ios.deploymentTarget = "14.1"
        podfile = project.file("../MomentumiOS/Podfile")
        framework {
            baseName = "MomentumSDK"
            isStatic = true

        }
    }
    
    sourceSets {
        val ktorVersion = "2.0.2"
        val serialization = "1.3.2"
        val firebase = "1.6.2"
        val sqlDelight = "1.5.3"
        val commonMain by getting {
            dependencies {
                // Stately
                implementation("co.touchlab:stately-common:1.2.1")
                implementation("co.touchlab:stately-isolate:1.2.1")
                implementation("co.touchlab:stately-iso-collections:1.2.1")
                // Ktor
                implementation("io.ktor:ktor-client-core:$ktorVersion")
                implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
                implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
                implementation("io.ktor:ktor-client-logging:$ktorVersion")
                // Koin
                implementation("io.insert-koin:koin-core:3.2.0")
                // SQLDelight
                implementation("com.squareup.sqldelight:runtime:$sqlDelight")
                // Kotlin FirebaseAuth
                implementation("dev.gitlive:firebase-auth:$firebase")
                // Kotlin Firestore
                implementation("dev.gitlive:firebase-firestore:$firebase")
                // Kotlin Serialization
                implementation ("org.jetbrains.kotlinx:kotlinx-serialization-json:$serialization")
                // Kotlin datetime
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")
                // Multiplatform Preferences
                implementation("com.russhwolf:multiplatform-settings-no-arg:0.9")
                // Cache4K
                implementation("io.github.reactivecircus.cache4k:cache4k:0.8.0")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-okhttp:$ktorVersion")
                implementation("com.squareup.sqldelight:android-driver:$sqlDelight")
                implementation("dev.gitlive:firebase-auth:$firebase")
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
                implementation("io.ktor:ktor-client-ios:$ktorVersion")
                implementation ("com.squareup.sqldelight:native-driver:$sqlDelight")
                implementation ("org.jetbrains.kotlinx:kotlinx-serialization-json:$serialization")
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
            dependencies {
                implementation("dev.gitlive:firebase-auth:$firebase")
                implementation ("org.jetbrains.kotlinx:kotlinx-serialization-json:$serialization")
            }
        }
    }

}

android {
    compileSdk = 32
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = 21
        targetSdk = 31
    }
    namespace = "com.mwaibanda.momentum"
}

sqldelight {
    database("MomentumDatabase") {
        packageName = "com.mwaibanda.momentum.data.db"
        sourceFolders = listOf("kotlin")
        linkSqlite = true
        version = "1.0.2"
    }
}
