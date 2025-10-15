package com.agelousis.noteitdown.application

import android.app.Application
import coil3.SingletonImageLoader
import com.agelousis.noteitdown.network.coil.ImageLoaderFactoryUtils

class BaseApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        //region Configure COIL Http
        configureCoil()
        //endregion
    }

    private fun configureCoil() {
        SingletonImageLoader.setSafe(
            factory = ImageLoaderFactoryUtils()
        )
    }

}