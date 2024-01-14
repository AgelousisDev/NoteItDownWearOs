buildscript {
    val agpVersion by extra(
        initialValue = Versions.gradlePlugin
    )
}// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version Versions.gradlePlugin apply false
    id("com.android.library") version Versions.gradlePlugin apply false
    id("org.jetbrains.kotlin.android") version Versions.kotlinVersion apply false
}