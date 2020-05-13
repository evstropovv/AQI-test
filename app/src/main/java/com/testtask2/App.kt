package com.testtask2

import android.app.Application
import com.testtask2.di.AppComponent
import com.testtask2.di.AppModule
import com.testtask2.di.DaggerAppComponent
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
class App : Application() {

    private var component: AppComponent? = null

    override fun onCreate() {
        super.onCreate()

        component = DaggerAppComponent
            .builder()
            .appModule(AppModule(this))
            .build()
    }

    fun getComponent(): AppComponent? {
        return component
    }

}
