apply {
    from("$rootDir/base-module.gradle")
}
dependencies {
    "implementation"(project(Modules.shopoverviewDomain))
    "implementation"(project(Modules.core))
}