apply {
    from("$rootDir/base-module.gradle")
}
dependencies {
    "implementation"(project(Modules.productoverviewDomain))
    "implementation"(project(Modules.core))
}