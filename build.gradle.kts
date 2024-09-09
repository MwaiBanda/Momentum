plugins {
    alias(libs.plugins.androidApplication).apply(false)
    alias(libs.plugins.androidLibrary).apply(false)
    alias(libs.plugins.kotlinAndroid).apply(false)
    alias(libs.plugins.kotlinMultiplatform).apply(false)
    alias(libs.plugins.compose.compiler) apply false
    id("com.google.gms.google-services") version "4.4.2" apply false
}

val secretPropsFile = project.rootProject.file("local.properties")
if (secretPropsFile.exists()) {
    secretPropsFile.reader().use {
        java.util.Properties().apply {
            load(it)
        }
    }.onEach { (name, value) ->
        ext[name.toString()] = value
    }
}

buildscript {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    dependencies {
        classpath(libs.kotlin.gradle.plugin)
        classpath(libs.gradle)
        classpath(libs.gradle.plugin)
        classpath(libs.google.services)
    }
}

allprojects {

    repositories {

        google()
        mavenCentral()
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/MwaiBanda/Authentication")
            credentials {
                username = getExtraString("GITHUB_EMAIL")
                password = getExtraString("GITHUB_TOKEN")
            }
        }
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

fun getExtraString(name: String) = ext[name]?.toString()
