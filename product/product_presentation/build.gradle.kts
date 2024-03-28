plugins {
    id("com.android.library")
    id("kotlin-android")
}

android {
    namespace = "com.eshop.product_presentation"
}

apply {
    from("$rootDir/compose-module.gradle")
}

dependencies {
    "implementation"(project(Modules.coreui))
    "implementation"(project(Modules.core))
    "implementation"(project(Modules.productDomain))
}