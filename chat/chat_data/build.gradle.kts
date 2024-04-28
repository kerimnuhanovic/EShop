apply {
    from("$rootDir/base-module.gradle")
}
dependencies {
    "implementation"(project(Modules.chatDomain))
    "implementation"(project(Modules.core))
    "implementation"(Socket.socket) {
        exclude(group = "org.json", module = "json")
    }
    //"implementation"(Socket.socketEngine)
}