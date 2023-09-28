apply {
    from("$rootDir/base-module.gradle")
}
dependencies {
    "implementation"(project(Modules.signupDomain))
    "implementation"(project(Modules.core))
}