buildscript {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.0")
        classpath("com.android.tools.build:gradle:7.2.1")
        classpath ("com.squareup.sqldelight:gradle-plugin:1.5.3")
        classpath ("com.google.gms:google-services:4.3.13")

    }
}

allprojects {

    repositories {
        google()
        mavenCentral()
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}