package com.jvmori.cryptocurrencyapp.cryptolist.presentation.di

import com.jvmori.cryptocurrencyapp.cryptolist.data.remote.CryptoApi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    factory { provideInterceptor() }
    factory { provideOkHttpClient(get()) }
    factory { provideApi(get()) }
    single { provideRetrofit(get()) }
}

fun provideApi(retrofit: Retrofit): CryptoApi {
    return retrofit.create(CryptoApi::class.java)
}

fun provideRetrofit(client: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl("https://api.coinlore.com/api/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
}

fun provideOkHttpClient(interceptor: Interceptor): OkHttpClient {
    return OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .retryOnConnectionFailure(true)
        .build()
}

fun provideInterceptor(): Interceptor {
    return Interceptor { chain ->
        val url = chain.request()
            .url()
            .newBuilder()
            .build()
        val request = chain.request()
            .newBuilder()
            .url(url)
            .build()
        return@Interceptor chain.proceed(request)
    }
}