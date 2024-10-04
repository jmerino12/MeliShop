pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "MeliShop"
include(":app")
include(":features:detail:domain")
include(":features:detail:infrastructure")
include(":core:network")
include(":core:common")
include(":features:search:domain")
include(":features:search:infrastructure")
include(":features:search:presentation")
