plugins {
    id("com.android.library")
    id("kotlin-android")
}

android {
    namespace = "com.eshop.core"
}

apply {
    from("$rootDir/base-module.gradle")
}

dependencies {
}