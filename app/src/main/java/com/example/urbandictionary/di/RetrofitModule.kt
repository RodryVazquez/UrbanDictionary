package com.example.urbandictionary.di

import com.example.urbandictionary.BuildConfig
import com.example.urbandictionary.repositories.UrbanDictionaryService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

const val baseUrl: String = "https://mashape-community-urban-dictionary.p.rapidapi.com"

@Module
@InstallIn(ApplicationComponent::class)
object RetrofitModule {

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Singleton
    @Provides
    fun provideRetrofitService(
        okHttpClient: OkHttpClient,
        gson: Gson
    ): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    fun provideOpenOkHttp(): OkHttpClient {
        val client = OkHttpClient.Builder()

        client.apply {
            addInterceptor(HttpLoggingInterceptor().apply {
                level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
            })
            addInterceptor { chain ->
                val newClient = chain.request().newBuilder()
                    .addHeader(
                        "x-rapidapi-key",
                        ""
                    )
                    .addHeader(
                        "x-rapidapi-host",
                        "mashape-community-urban-dictionary.p.rapidapi.com"
                    )

                return@addInterceptor chain.proceed(newClient.build())
            }
        }
        return client.build()
    }

    @Provides
    fun provideUrbanDictionaryService(
        retrofit: Retrofit
    ): UrbanDictionaryService =
        retrofit.create(UrbanDictionaryService::class.java)
}