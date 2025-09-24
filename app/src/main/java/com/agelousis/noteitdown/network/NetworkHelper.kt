package com.agelousis.noteitdown.network

import com.agelousis.noteitdown.BuildConfig
import com.agelousis.noteitdown.network.enumerations.NetworkHeader
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object NetworkHelper {

    private const val USER_AGENT = "NoteItDownApp/1.0 (Android; NoteIdDown Wear OS APP)"

    val headersMap
        get() = listOf(
            NetworkHeader.USER_AGENT to USER_AGENT
        )

    val network: Retrofit? by lazy {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val headerInterceptor = Interceptor { chain ->
            val originalRequest = chain.request()
            val requestWithHeaders = originalRequest.newBuilder()
            headersMap.forEach { (header, value) ->
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
        val client = OkHttpClient.Builder()
            .addInterceptor(
                interceptor = headerInterceptor
            )
            .addInterceptor(
                interceptor = interceptor
            )
            .connectTimeout(
                timeout = 60,
                unit = TimeUnit.SECONDS
            )
            .readTimeout(
                timeout = 60,
                unit = TimeUnit.SECONDS
            )
            .writeTimeout(
                timeout = 60,
                unit = TimeUnit.SECONDS
            )
            .build()
        Retrofit.Builder()
            .client(client)
            .baseUrl(BuildConfig.WIKIPEDIA_IMAGE_SEARCH_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    inline fun <reified T> create() = network?.create(T::class.java)

}