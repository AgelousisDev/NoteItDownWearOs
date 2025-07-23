plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.agelousis.noteitdown"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.agelousis.noteitdown"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = libs.versions.versionCode.get().toInt()
        versionName = libs.versions.versionName.get()

    }

    buildTypes {
        getByName("debug") {
            signingConfig = signingConfigs.getByName(name = "debug")
            isDebuggable = true
            buildConfigField(type = "String", name = "WIKIPEDIA_IMAGE_SEARCH_BASE_URL", value = "\"https://en.wikipedia.org/w/api.php/\"")
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile(
                    name = "proguard-android-optimize.txt"
                ),
                "proguard-rules.pro"
            )
            buildConfigField(type = "String", name = "WIKIPEDIA_IMAGE_SEARCH_BASE_URL", value = "\"https://en.wikipedia.org/w/api.php/\"")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    kotlin {
        jvmToolchain(
            jdkVersion = 17
        )
    }
    packaging {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }
}

dependencies {
    implementation(libs.play.services.wearable)
    implementation(platform(libs.compose.bom))
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.compose.material)
    implementation(libs.compose.foundation)
    implementation(libs.wear.tooling.preview)
    implementation(libs.activity.compose)
    implementation(libs.core.splashscreen)
    implementation(libs.wear.compose.navigation)
    implementation(libs.wear.compose.materialYou)
    implementation(libs.compose.material.icons)
    implementation(libs.compose.material.icons.extended)
    implementation(libs.wear.input)
    implementation(libs.glance)
    implementation(libs.glance.app.widget)
    implementation(libs.glance.wear.tiles)
    implementation(libs.retrofit2.converter.gson)
    implementation(libs.http3.logging.interceptor)
    implementation(libs.data.store.preferences)
    implementation(libs.coil.compose)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.ui.test.junit4)
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)
    /*implementation(Dependencies.coreKtx)
    implementation(Dependencies.dataStorePreferences)
    implementation(Dependencies.compose)
    implementation(Dependencies.composeMaterialIconsCore)
    implementation(Dependencies.composeMaterialIconsExtended)
    //implementation(Dependencies.wearComposeMaterial)
    implementation(Dependencies.wearComposeMaterialThree)
    implementation(Dependencies.wearComposeFoundation)
    implementation(Dependencies.wearInput)
    implementation(Dependencies.composeUiToolingPreview)
    implementation(Dependencies.lifecycleRuntimeKtx)
    implementation(Dependencies.activityCompose)
    implementation(Dependencies.lifecycleViewModelCompose)
    implementation(Dependencies.constraintLayoutCompose)
    implementation(Dependencies.composeRuntimeLiveData)
    implementation(Dependencies.glance)
    implementation(Dependencies.coilCompose)
    //implementation(Dependencies.glanceAppWidget)
    implementation(Dependencies.glanceWearTiles)
    implementation(Dependencies.wearToolingPreviewDevices)
    implementation(Dependencies.wearComposeNavigation)
    implementation(Dependencies.retrofit2ConverterGson)
    implementation(Dependencies.http3LoggingInterceptor)
    androidTestImplementation(Dependencies.composeUiTest)
    debugImplementation(Dependencies.composeUiTooling)
    debugImplementation(Dependencies.composeUiTestManifest)
    implementation(kotlin("reflect"))*/
}