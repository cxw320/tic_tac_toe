package com.example.api

import com.example.api.BuildConfig.API_URL
import com.example.api.networking.NetworkResponseAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object ApiKoinModule {

    val networkModule = module {
        single { provideOkHttpClient() }
        single { provideMoshi() }
        single { provideRetrofit(get(),get()) }
        single { provideBambooApi(get()) }
    }

    fun provideRetrofit(okHttpClient: OkHttpClient, moshi: Moshi): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_URL)
            .addCallAdapterFactory(NetworkResponseAdapterFactory())
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    fun provideMoshi(): Moshi {
        return Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    }

    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient().newBuilder()
            .addInterceptor(
                HttpLoggingInterceptor()
                .apply{level= HttpLoggingInterceptor.Level.BODY})
            .build()
    }

    fun provideBambooApi(retrofit: Retrofit): TicTacToeApi{
        return retrofit.create(TicTacToeApi::class.java)
    }
}