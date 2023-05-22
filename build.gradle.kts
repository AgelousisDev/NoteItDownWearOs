buildscript {
    /*ext {
        compose_version = '1.4.0-alpha01'
        wear_compose_version = '1.1.0-beta01'
    }*/
}// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    val kotlinVersion = "1.8.21"
    id("com.android.application") version "8.0.1" apply false
    id("com.android.library") version "8.0.1" apply false
    id("org.jetbrains.kotlin.android") version kotlinVersion apply false

}