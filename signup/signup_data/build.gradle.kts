plugins {
    id("com.android.library")
    id("kotlin-android")
}

android {
    namespace = "com.eshop.signup_data"
}

apply {
    from("$rootDir/base-module.gradle")
}
dependencies {
    "implementation"(project(Modules.signupDomain))
    "implementation"(project(Modules.core))
}