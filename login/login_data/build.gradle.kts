apply {
    from("$rootDir/base-module.gradle")
}
dependencies {
    "implementation"(project(Modules.loginDomain))
    "implementation"(project(Modules.core))
    /*"implementation"(Retrofit.retrofit)
    "implementation"(Moshi.moshi)*/
}