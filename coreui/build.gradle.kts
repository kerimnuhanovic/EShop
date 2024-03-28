plugins {
    id("com.android.library")
    id("kotlin-android")
}

android {
    namespace = "com.eshop.coreui"
}

apply {
    from("$rootDir/compose-module.gradle")
}