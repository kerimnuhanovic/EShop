plugins {
    id("com.android.library")
    id("kotlin-android")
}

android {
    namespace = "com.eshop.signup_domain"
}


apply {
    from("$rootDir/base-module.gradle")
}

dependencies {
    "implementation"(project(Modules.core))
}