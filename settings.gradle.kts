pluginManagement {
    repositories {
        maven {
            url = uri("https://maven.myket.ir")
            isAllowInsecureProtocol = true
        }
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        maven {
            url = uri("https://maven.myket.ir")
            isAllowInsecureProtocol = true
        }
    }
}

rootProject.name = "MindFlow-Offline-App"
include(":app")
