
plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.parcelize")
    id("com.github.ben-manes.versions") version "0.46.0"
}

val composeVersion = "1.5.0-alpha03"
val composeActivityVersion = "1.8.0-alpha03"
val coreKtxVersion = "1.12.0-alpha03"
val googlePlayServicesWearableVersion = "18.0.0"
val percentLayoutVersion = "1.0.0"
val wearComposeVersion = "1.2.0-alpha09"
val liveDataViewModelVersion = "2.6.1"
val dataStorePreferenceVersion = "1.0.0"
val constraintLayoutComposeVersion = "1.1.0-alpha09"

android {
    namespace = "com.agelousis.noteitdown"
    compileSdkPreview = "UpsideDownCake"

    defaultConfig {
        applicationId = "com.agelousis.noteitdown"
        minSdk = 28
        targetSdkPreview = "UpsideDownCake"
        versionCode = 1
        versionName = "1.0"

    }

    buildTypes {
        getByName("debug") {
            signingConfig = signingConfigs.getByName(name = "debug")
            isDebuggable = true
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile(
                    name = "proguard-android-optimize.txt"
                ),
                "proguard-rules.pro"
            )
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
        kotlinCompilerExtensionVersion = "1.4.6"
    }
    packagingOptions {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:$coreKtxVersion")
    //implementation("com.google.android.gms:play-services-wearable:$googlePlayServicesWearableVersion")
    // Datastore
    implementation("androidx.datastore:datastore-preferences:$dataStorePreferenceVersion")
    //implementation("androidx.percentlayout:percentlayout:$percentLayoutVersion")
    implementation("androidx.compose.ui:ui:$composeVersion")
    //implementation("androidx.compose.material:material:$composeVersion")
    implementation("androidx.compose.material:material-icons-extended:$composeVersion")
    implementation("androidx.wear.compose:compose-material:$wearComposeVersion")
    implementation("androidx.wear.compose:compose-foundation:$wearComposeVersion")
    implementation("androidx.wear:wear-input:1.2.0-alpha02")
    implementation("androidx.compose.ui:ui-tooling-preview:$composeVersion")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation("androidx.activity:activity-compose:$composeActivityVersion")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:$liveDataViewModelVersion")
    implementation("androidx.constraintlayout:constraintlayout-compose:$constraintLayoutComposeVersion")
    implementation("androidx.compose.runtime:runtime-livedata:$composeVersion")
    //implementation("io.coil-kt:coil-compose:2.2.2")
    implementation("androidx.glance:glance-wear-tiles:1.0.0-alpha05")
    // Navigation
    implementation("androidx.wear.compose:compose-navigation:$wearComposeVersion")

    // ZXING
    implementation("com.journeyapps:zxing-android-embedded:4.3.0")
    // Retrofit & OkHttp
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.8")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:$composeVersion")
    debugImplementation("androidx.compose.ui:ui-tooling:$composeVersion")
    debugImplementation("androidx.compose.ui:ui-test-manifest:$composeVersion")
    implementation(kotlin("reflect"))
}