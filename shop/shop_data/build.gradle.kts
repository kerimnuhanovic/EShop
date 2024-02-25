apply {
    from("$rootDir/base-module.gradle")
}
dependencies {
    "implementation"(project(Modules.shopDomain))
    "implementation"(project(Modules.core))
}