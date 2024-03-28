plugins {
    id("com.android.library")
    id("kotlin-android")
}

android {
    namespace = "com.eshop.productoverview_data"
}

apply {
    from("$rootDir/base-module.gradle")
}
dependencies {
    "implementation"(project(Modules.productoverviewDomain))
    "implementation"(project(Modules.core))
}