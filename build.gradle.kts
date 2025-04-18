// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    dependencies {
        classpath(libs.gradlePlugin)
        classpath(libs.kotlinGradlePlugin)
    }
}

plugins {
    // ./gradlew versionCatalogUpdate
    id("com.github.ben-manes.versions") version "0.44.0"
    id("nl.littlerobots.version-catalog-update") version "0.8.0"
    id("org.jlleitschuh.gradle.ktlint") version "11.1.0"
    id("org.jetbrains.kotlin.android") version "2.0.0" apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.android.application) apply false
}

tasks.register<Delete>("clean") {
    delete(rootProject.layout.buildDirectory)
}

val isNonStable: (String) -> Boolean = { version ->
    val stableKeyword = listOf("RELEASE", "FINAL", "GA").any {
        version.uppercase().contains(it)
    }
    val regex = "^[0-9,.v-]+(-r)?$".toRegex()
    !stableKeyword && !regex.matches(version)
}

tasks.named<com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask>("dependencyUpdates") {
    rejectVersionIf {
        isNonStable(candidate.version) && !isNonStable(currentVersion)
    }
}

ext {
    set("minSdkVersion", 30)
    set("compileSdkVersion", 34)
    set("targetSdkVersion", 30)
    // 0010020001
    // 001 - major
    // 002 - minor
    // 001 - patch
}
