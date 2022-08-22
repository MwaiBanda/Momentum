plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("plugin.serialization") version "1.7.0"
    id("com.google.gms.google-services")
}

android {
    compileSdk = 32
    defaultConfig {
        applicationId = "com.mwaibanda.momentum.android"
        minSdk = 21
        targetSdk = 31
        versionCode = 5
        versionName = "1.0.3"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

    }
    buildFeatures {
        compose = true
    }

    // Set both the Java and Kotlin compilers to target Java 8.
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility  = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.2.0"
    }
    testOptions {
        unitTests.isIncludeAndroidResources  = true
        unitTests.isReturnDefaultValues = true
        execution = "ANDROID_TEST_ORCHESTRATOR"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
}

dependencies {
    implementation(project(":MomentumSDK"))
    implementation ("androidx.core:core-ktx:1.8.0")
    implementation ("androidx.appcompat:appcompat:1.5.0")
    implementation ("com.google.android.material:material:1.6.1")

    // Integration with activities
    implementation( "androidx.activity:activity-compose:1.5.1")
    // Compose Material Design
    implementation( "androidx.compose.material:material:1.2.0")
    // Animations
    implementation("androidx.compose.animation:animation:1.2.0")
    // Tooling support (Previews, etc.)
    implementation( "androidx.compose.ui:ui-tooling:1.2.0")
    // Integration with ViewModels
    implementation( "androidx.lifecycle:lifecycle-viewmodel-compose:2.5.1")
    implementation("com.google.firebase:firebase-auth:21.0.7")
    implementation("junit:junit:4.13.2")
    // UI Tests
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.2.0")
    //Unit Testing
    testImplementation ("junit:junit:4.13.2")
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.2")
    testImplementation ("org.junit.jupiter:junit-jupiter-engine:5.8.2")
    testImplementation ("androidx.arch.core:core-testing:2.1.0")
    testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4")
    testImplementation ("org.robolectric:robolectric:4.8.1")
    testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4")
    testImplementation ("org.mockito:mockito-core:4.6.1")
    testImplementation ("app.cash.turbine:turbine:0.8.0")
    // Navigation
    implementation( "androidx.navigation:navigation-compose:2.5.1")
    // Icons exts
    implementation( "androidx.compose.material:material-icons-extended:1.2.0")
    // Status Bar
    implementation ("com.google.accompanist:accompanist-systemuicontroller:0.25.0")
    implementation ("com.google.accompanist:accompanist-insets:0.25.0")
    // Stripe Android SDK
    implementation ("com.stripe:stripe-android:19.0.0")
    //Koin
    implementation("io.insert-koin:koin-android:3.2.0")
    implementation("io.insert-koin:koin-core:3.2.0")
    // Koin Jetpack Compose
    implementation("io.insert-koin:koin-androidx-compose:3.2.0")
    implementation("io.insert-koin:koin-androidx-navigation:3.2.0")
    //Lottie
    implementation("com.airbnb.android:lottie-compose:4.0.0")
    // Bottom Sheet
    implementation("com.google.accompanist:accompanist-navigation-material:0.25.0")
    // Firebase Auth Kotlin SDK
    implementation("dev.gitlive:firebase-auth:1.6.1")
}


tasks.withType<Test> {
    useJUnitPlatform()
}