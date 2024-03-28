plugins {
    id("com.android.library")
    id("kotlin-android")
}

android {
    namespace = "com.eshop.shopoverview_data"
}

apply {
    from("$rootDir/base-module.gradle")
}
dependencies {
    "implementation"(project(Modules.shopoverviewDomain))
    "implementation"(project(Modules.core))
}