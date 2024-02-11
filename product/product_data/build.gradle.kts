apply {
    from("$rootDir/base-module.gradle")
}
dependencies {
    "implementation"(project(Modules.productDomain))
    "implementation"(project(Modules.core))
}