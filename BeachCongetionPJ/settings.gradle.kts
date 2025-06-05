pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "BeachCongetionPJ"

include(":presentation")
include(":data")
include(":domain")
include(":app")
include(":core")
include(":feature")
include(":library")
include(":library:design-system")
include(":feature:main")
include(":library:design")
