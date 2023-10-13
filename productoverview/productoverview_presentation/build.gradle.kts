apply {
    from("$rootDir/compose-module.gradle")
}

dependencies {
    "implementation"(Lottie.lottie)
    "implementation"(project(Modules.core))
    "implementation"(project(Modules.coreui))
    "implementation"(project(Modules.productoverviewDomain))
}