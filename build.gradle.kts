buildscript {
    /*ext {
        compose_version = '1.4.0-alpha01'
        wear_compose_version = '1.1.0-beta01'
    }*/
}// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version Versions.gradlePlugin apply false
    id("com.android.library") version Versions.gradlePlugin apply false
    id("org.jetbrains.kotlin.android") version Versions.kotlinVersion apply false
}