apply {
    from("$rootDir/base-module.gradle")
}
dependencies {
    "implementation"(project(Modules.cartDomain))
    "implementation"(project(Modules.core))
}