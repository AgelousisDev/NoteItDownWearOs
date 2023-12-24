
plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.parcelize")
    id("com.github.ben-manes.versions") version Versions.githubBanesVersion
}

android {
    namespace = ConfigData.packageName
    compileSdk = ConfigData.compileSdkVersion

    defaultConfig {
        applicationId = "com.agelousis.noteitdown"
        minSdk = ConfigData.minSdkVersion
        targetSdk = ConfigData.targetSdkVersion
        versionCode = ConfigData.versionCodeVersion
        versionName = ConfigData.versionNameVersion

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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Versions.kotlinCompilerExtensionVersion
    }
    packaging {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }
}

dependencies {
    implementation(Dependencies.coreKtx)
    implementation(Dependencies.dataStorePreferences)
    implementation(Dependencies.compose)
    implementation(Dependencies.composeMaterialIconsExtended)
    implementation(Dependencies.wearComposeMaterial)
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
    implementation(kotlin("reflect"))
}