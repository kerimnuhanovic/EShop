// Top-level build.gradle file
buildscript {
    repositories {
        google()
        mavenCentral()
        //gradlePluginPortal()
    }
    dependencies {
        classpath(Build.buildGradle)
        classpath(Build.kotlinGradlePlugin)
        classpath(Build.hiltGradlePlugin)
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.21")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}