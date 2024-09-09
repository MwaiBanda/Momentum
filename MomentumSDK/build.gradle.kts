plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    kotlin("plugin.serialization") version "2.0.20"
    id("com.android.library")
    id( "com.squareup.sqldelight")
    id("kotlin-parcelize")
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
        ios.deploymentTarget = "14.0"
        podfile = project.file("../MomentumiOS/Podfile")
        framework {
            baseName = "MomentumSDK"
            isStatic = true
        }
    }
    
    sourceSets {
        commonMain.dependencies {
            // Stately
            implementation(libs.stately.common)
            implementation(libs.stately.isolate)
            implementation(libs.stately.iso.collections)
            // Ktor
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.ktor.client.logging)
            // Koin
            implementation(libs.koin.core)
            // SQLDelight
            implementation(libs.sqldelight.runtime)
            // Kotlin Serialization
            implementation (libs.kotlinx.serialization.json)
            // Kotlin datetime
            implementation(libs.kotlinx.datetime)
            // Multiplatform Preferences
            implementation(libs.multiplatform.settings.no.arg)
            // Cache4K
            implementation(libs.cache4k)
            api(libs.authentication)
        }

        commonTest.dependencies {
            implementation(kotlin("test"))
        }

        androidMain.dependencies {
            implementation(libs.ktor.client.okhttp)
            implementation(libs.android.driver)
        }

        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
            implementation (libs.native.driver)
            implementation (libs.kotlinx.serialization.json)
            implementation(libs.firebase.auth)
        }

        iosTest.dependencies {
            implementation(libs.firebase.auth)
            implementation (libs.kotlinx.serialization.json)
            implementation (libs.native.driver)
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
        implementation(libs.androidx.core.i18n)
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
