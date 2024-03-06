buildscript {
    val agpVersion by extra(
        initialValue = Versions.GRADLE_PLUGIN
    )
}// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version Versions.GRADLE_PLUGIN apply false
    id("com.android.library") version Versions.GRADLE_PLUGIN apply false
    id("org.jetbrains.kotlin.android") version Versions.KOTLIN_VERSION apply false
}