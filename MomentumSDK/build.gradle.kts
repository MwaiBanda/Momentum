plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    kotlin("plugin.serialization") version "1.9.10"
    id("com.android.library")
    id( "com.squareup.sqldelight")
    id("kotlin-parcelize")
    id("com.rickclephas.kmp.nativecoroutines") version "1.0.0-ALPHA-24"
}


version = "1.0.0"

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "17"
            }
        }
    }
    sourceSets.all {
        languageSettings.optIn("kotlin.experimental.ExperimentalObjCName")
    }
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    applyDefaultHierarchyTemplate()

    // For example:
    jvmToolchain(17)
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
        val ktorVersion = "2.3.8"
        val serialization = "1.6.2"
        val firebase = "1.6.2"
        val sqlDelight = "1.5.4"

        commonMain.dependencies {
            // Stately
            implementation("co.touchlab:stately-common:1.2.5")
            implementation("co.touchlab:stately-isolate:1.2.1")
            implementation("co.touchlab:stately-iso-collections:1.2.1")
            // Ktor
            implementation("io.ktor:ktor-client-core:$ktorVersion")
            implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
            implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
            implementation("io.ktor:ktor-client-logging:$ktorVersion")
            implementation("io.ktor:ktor-client-cio:$ktorVersion")
            // Koin
            implementation("io.insert-koin:koin-core:3.5.0")
            // SQLDelight
            implementation("com.squareup.sqldelight:runtime:$sqlDelight")
            // Kotlin Serialization
            implementation ("org.jetbrains.kotlinx:kotlinx-serialization-json:$serialization")
            // Kotlin datetime
            implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")
            // Multiplatform Preferences
            implementation("com.russhwolf:multiplatform-settings-no-arg:1.0.0")
            // Cache4K
            implementation("io.github.reactivecircus.cache4k:cache4k:0.8.0")
            api("io.github.mwaibanda:authentication:1.0.5")
        }

        commonTest.dependencies {
            implementation(kotlin("test"))
        }

        androidMain.dependencies {
            implementation("io.ktor:ktor-client-okhttp:$ktorVersion")
            implementation("com.squareup.sqldelight:android-driver:$sqlDelight")
        }

        iosMain.dependencies {
            implementation("io.ktor:ktor-client-ios:$ktorVersion")
            implementation ("com.squareup.sqldelight:native-driver:$sqlDelight")
            implementation ("org.jetbrains.kotlinx:kotlinx-serialization-json:$serialization")

        }

        iosTest.dependencies {
            implementation("dev.gitlive:firebase-auth:$firebase")
            implementation ("org.jetbrains.kotlinx:kotlinx-serialization-json:$serialization")
            implementation ("com.squareup.sqldelight:native-driver:$sqlDelight")
        }
    }

}

android {
    compileSdk = 34
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = 26
    }
    namespace = "com.mwaibanda.momentum"
    dependencies {
        implementation("androidx.core:core-i18n:1.0.0-alpha01")
    }
}

sqldelight {
    database("MomentumDatabase") {
        packageName = "com.mwaibanda.momentum.data.db"
        sourceFolders = listOf("kotlin")
        linkSqlite = true
        version = "1.0.2"
    }
}
