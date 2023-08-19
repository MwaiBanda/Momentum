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
        val kotlinVersion = "1.9.0"

        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.0")
        classpath("com.android.tools.build:gradle:8.1.0")
        classpath ("com.squareup.sqldelight:gradle-plugin:1.5.4")
        classpath ("com.google.gms:google-services:4.3.15")
        classpath(kotlin("gradle-plugin", version = kotlinVersion))


    }
}

allprojects {

    repositories {

        google()
        mavenCentral()
        maven {
            name = "GitHubPackages"
            url = uri(getExtraString("SOURCE_URL").toString())
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
