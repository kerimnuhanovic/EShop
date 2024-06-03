apply {
    from("$rootDir/base-module.gradle")
}
dependencies {
    "implementation"(project(Modules.favouriteShopsDomain))
    "implementation"(project(Modules.core))
}