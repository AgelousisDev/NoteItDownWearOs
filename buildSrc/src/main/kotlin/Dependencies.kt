/**
 * To define dependencies
 */
object Dependencies {
    val coreKtx by lazy { "androidx.core:core-ktx:${Versions.CORE_KTX_VERSION}" }
    val dataStorePreferences by lazy { "androidx.datastore:datastore-preferences:${Versions.DATA_STORE_PREFERENCE_VERSION}" }
    val compose by lazy { "androidx.compose.ui:ui:${Versions.COMPOSE_VERSION}" }
    val composeMaterialIconsExtended by lazy { "androidx.compose.material:material-icons-extended:${Versions.COMPOSE_VERSION}" }
    val wearComposeMaterial by lazy { "androidx.wear.compose:compose-material:${Versions.WEAR_COMPOSE_VERSION}" }
    val wearComposeMaterialThree by lazy { "androidx.wear.compose:compose-material3:${Versions.WEAR_COMPOSE_MATERIAL_YOU_VERSION}" }
    val wearComposeFoundation by lazy { "androidx.wear.compose:compose-foundation:${Versions.WEAR_COMPOSE_VERSION}" }
    val wearInput by lazy { "androidx.wear:wear-input:${Versions.WEAR_INPUT_VERSION}" }
    val composeUiToolingPreview by lazy { "androidx.compose.ui:ui-tooling-preview:${Versions.COMPOSE_VERSION}" }
    val lifecycleRuntimeKtx by lazy { "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.LIFECYCLE_RUNTIME_KTX_VERSION}" }
    val activityCompose by lazy { "androidx.activity:activity-compose:${Versions.COMPOSE_ACTIVITY_VERSION}" }
    val lifecycleViewModelCompose by lazy { "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.LIVE_DATA_VIEW_MODEL_VERSION}" }
    val constraintLayoutCompose by lazy { "androidx.constraintlayout:constraintlayout-compose:${Versions.CONSTRAINT_LAYOUT_COMPOSE_VERSION}" }
    val composeRuntimeLiveData by lazy { "androidx.compose.runtime:runtime-livedata:${Versions.COMPOSE_VERSION}" }
    // For Glance support
    val glance by lazy { "androidx.glance:glance:${Versions.GLANCE_VERSION}" }
    // For AppWidgets support
    val glanceAppWidget by lazy { "androidx.glance:glance-appwidget:${Versions.GLANCE_APP_WIDGET_VERSION}" }
    // For Wear-Tiles support
    val glanceWearTiles by lazy { "androidx.glance:glance-wear-tiles:${Versions.GLANCE_WEAR_TILE_VERSION}" }
    val wearComposeNavigation by lazy { "androidx.wear.compose:compose-navigation:${Versions.WEAR_COMPOSE_VERSION}" }
    val retrofit2ConverterGson by lazy { "com.squareup.retrofit2:converter-gson:${Versions.RETROFIT_CONVERTER_GSON_VERSION}" }
    val http3LoggingInterceptor by lazy { "com.squareup.okhttp3:logging-interceptor:${Versions.HTTP_LOGGING_INTERCEPTOR_VERSION}" }
    val composeUiTest by lazy { "androidx.compose.ui:ui-test-junit4:${Versions.COMPOSE_VERSION}" }
    val composeUiTooling by lazy { "androidx.compose.ui:ui-tooling:${Versions.COMPOSE_VERSION}" }
    val composeUiTestManifest by lazy { "androidx.compose.ui:ui-test-manifest:${Versions.COMPOSE_VERSION}" }
    val wearToolingPreviewDevices by lazy { "androidx.wear:wear-tooling-preview:${Versions.WEAR_TOOLING_PREVIEW_DEVICES_VERSION}" }
    val coilCompose by lazy { "io.coil-kt:coil-compose:${Versions.COIL_COMPOSE_VERSION}" }
}