plugins {
    id("com.android.library")
    id("kotlin-android")
}

android {
    namespace = "com.eshop.onboarding_presentation"
}

apply {
    from("$rootDir/compose-module.gradle")
}

dependencies {
}