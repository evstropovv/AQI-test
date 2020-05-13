package com.testtask2.di

import android.app.Application
import android.content.Context
import com.testtask2.BuildConfig
import com.testtask2.data.ApiRetrofit
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.InternalCoroutinesApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@UseExperimental(InternalCoroutinesApi::class)
@Module(includes = [ViewModelModule::class, DataModule::class])
public class AppModule(val application: Application) {

    @Provides
    @Singleton
    public fun provideContext(): Context {
        return application
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val logginInterceptor = HttpLoggingInterceptor()
        logginInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .addInterceptor(logginInterceptor)
            .addInterceptor { chain ->
                var request = chain.request()
                val url = request.url.newBuilder().addQueryParameter("token", BuildConfig.API_KEY).build()
                request = request.newBuilder().url(url).build()
                chain.proceed(request)
            }
            .build()
    }


    @Provides
    @Singleton
    fun provideRetrofit(interceptor: OkHttpClient): ApiRetrofit {
        val retrofit = Retrofit.Builder()
            .client(interceptor)
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(ApiRetrofit::class.java)
    }
}
