plugins {
    id("com.android.library")
    id("kotlin-android")
}

android {
    namespace = "com.eshop.shop_data"
}

apply {
    from("$rootDir/base-module.gradle")
}
dependencies {
    "implementation"(project(Modules.shopDomain))
    "implementation"(project(Modules.core))
}