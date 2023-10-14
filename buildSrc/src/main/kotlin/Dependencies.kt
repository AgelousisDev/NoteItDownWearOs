/**
 * To define dependencies
 */
object Dependencies {
    val coreKtx by lazy { "androidx.core:core-ktx:${Versions.coreKtxVersion}" }
    val dataStorePreferences by lazy { "androidx.datastore:datastore-preferences:${Versions.dataStorePreferenceVersion}" }
    val compose by lazy { "androidx.compose.ui:ui:${Versions.composeVersion}" }
    val composeMaterialIconsExtended by lazy { "androidx.compose.material:material-icons-extended:${Versions.composeVersion}" }
    val wearComposeMaterial by lazy { "androidx.wear.compose:compose-material:${Versions.wearComposeVersion}" }
    val wearComposeFoundation by lazy { "androidx.wear.compose:compose-foundation:${Versions.wearComposeVersion}" }
    val wearInput by lazy { "androidx.wear:wear-input:${Versions.wearInputVersion}" }
    val composeUiToolingPreview by lazy { "androidx.compose.ui:ui-tooling-preview:${Versions.composeVersion}" }
    val lifecycleRuntimeKtx by lazy { "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycleRuntimeKtxVersion}" }
    val activityCompose by lazy { "androidx.activity:activity-compose:${Versions.composeActivityVersion}" }
    val lifecycleViewModelCompose by lazy { "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.liveDataViewModelVersion}" }
    val constraintLayoutCompose by lazy { "androidx.constraintlayout:constraintlayout-compose:${Versions.constraintLayoutComposeVersion}" }
    val composeRuntimeLiveData by lazy { "androidx.compose.runtime:runtime-livedata:${Versions.composeVersion}" }
    // For Glance support
    val glance by lazy { "androidx.glance:glance:${Versions.glanceVersion}" }
    // For AppWidgets support
    val glanceAppWidget by lazy { "androidx.glance:glance-appwidget:${Versions.glanceAppWidgetVersion}" }
    // For Wear-Tiles support
    val glanceWearTiles by lazy { "androidx.glance:glance-wear-tiles:${Versions.glanceWearTileVersion}" }
    val wearComposeNavigation by lazy { "androidx.wear.compose:compose-navigation:${Versions.wearComposeVersion}" }
    val retrofit2ConverterGson by lazy { "com.squareup.retrofit2:converter-gson:${Versions.retrofit2ConverterGsonVersion}" }
    val http3LoggingInterceptor by lazy { "com.squareup.okhttp3:logging-interceptor:${Versions.http3LoggingInterceptorVersion}" }
    val composeUiTest by lazy { "androidx.compose.ui:ui-test-junit4:${Versions.composeVersion}" }
    val composeUiTooling by lazy { "androidx.compose.ui:ui-tooling:${Versions.composeVersion}" }
    val composeUiTestManifest by lazy { "androidx.compose.ui:ui-test-manifest:${Versions.composeVersion}" }
    //implementation("com.google.android.gms:play-services-wearable:$googlePlayServicesWearableVersion")
    //implementation("androidx.percentlayout:percentlayout:$percentLayoutVersion")
    //implementation("androidx.compose.material:material:$composeVersion")
    //implementation("io.coil-kt:coil-compose:2.2.2")
    val wearToolingPreviewDevices by lazy { "androidx.wear:wear-tooling-preview:${Versions.wearToolingPreviewDevicesVersion}" }
}