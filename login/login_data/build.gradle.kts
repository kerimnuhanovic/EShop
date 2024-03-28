plugins {
    id("com.android.library")
    id("kotlin-android")
}

android {
    namespace = "com.eshop.login_data"
}

apply {
    from("$rootDir/base-module.gradle")
}
dependencies {
    "implementation"(project(Modules.loginDomain))
    "implementation"(project(Modules.core))
    /*"implementation"(Retrofit.retrofit)
    "implementation"(Moshi.moshi)*/
}