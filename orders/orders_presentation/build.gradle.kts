apply {
    from("$rootDir/compose-module.gradle")
}

dependencies {
    "implementation"(project(Modules.coreui))
    "implementation"(project(Modules.core))
    "implementation"(project(Modules.ordersDomain))
}