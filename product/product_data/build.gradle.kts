plugins {
    id("com.android.library")
    id("kotlin-android")
}

android {
    namespace = "com.eshop.product_data"
}

apply {
    from("$rootDir/base-module.gradle")
}
dependencies {
    "implementation"(project(Modules.productDomain))
    "implementation"(project(Modules.core))
}