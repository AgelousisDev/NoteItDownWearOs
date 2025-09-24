package com.agelousis.noteitdown.network.coil

import coil3.ImageLoader
import coil3.PlatformContext
import coil3.SingletonImageLoader
import coil3.network.okhttp.OkHttpNetworkFetcherFactory
import com.agelousis.noteitdown.network.NetworkHelper
import okhttp3.OkHttpClient


class ImageLoaderFactoryUtils: SingletonImageLoader.Factory {

    override fun newImageLoader(
        context: PlatformContext
    ): ImageLoader {
        // Define your User-Agent string
         // Customize this

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val originalRequest = chain.request()
                val requestWithHeaders = originalRequest.newBuilder()
                NetworkHelper.headersMap.forEach { (header, value) ->
                    requestWithHeaders.addHeader(
                        name = header.header,
                        value = value
                    )
                }
                chain.proceed(
                    request = requestWithHeaders
                        .build()
                )
            }
            .build()

        return ImageLoader.Builder(context)
            .components {
                add(
                    factory = OkHttpNetworkFetcherFactory(
                        callFactory = okHttpClient
                    )
                )
            }
            // Add other Coil configurations here if needed
            // .placeholder(R.drawable.placeholder)
            // .error(R.drawable.error)
            .build()
    }

}