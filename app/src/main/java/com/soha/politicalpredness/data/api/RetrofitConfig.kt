package com.soha.politicalpredness.data.api

import com.soha.politicalpredness.utils.Constants.Base_URL
import com.soha.politicalpredness.utils.ElectionAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class RetrofitConfig {
    private var retrofit: Retrofit? = null

    private val moshi = Moshi.Builder()
        .add(ElectionAdapter())
        .add(KotlinJsonAdapterFactory())
        .build()


    private fun getClient(): Retrofit? {
        if (retrofit == null) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            val httpClient = OkHttpClient.Builder()
            httpClient.addInterceptor { chain ->
                chain.proceed(chain.request().newBuilder().build())
            }

            httpClient.addInterceptor(logging)
            retrofit = Retrofit.Builder()
                .baseUrl(Base_URL)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .client(httpClient.build())
                .build()
        }
        return retrofit
    }

    fun getApiServices(): ApiInterface? {
        return getClient()?.create(ApiInterface::class.java)
    }
}