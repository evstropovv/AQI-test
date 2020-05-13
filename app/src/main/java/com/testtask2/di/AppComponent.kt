package com.testtask2.di

import com.testtask2.presentation.MainActivity
import com.testtask2.presentation.stations.FilterDialog
import com.testtask2.presentation.stations.StationsFragment
import com.testtask2.presentation.userinfo.StationInfoFragment
import dagger.Component
import kotlinx.coroutines.InternalCoroutinesApi
import javax.inject.Singleton


@InternalCoroutinesApi
@Singleton
@Component(modules = arrayOf(AppModule::class, DbModule::class))
interface AppComponent {

    fun inject(main: MainActivity)
    fun inject(fragment: StationsFragment)
    fun inject(fragment: StationInfoFragment)
    fun inject(fragment: FilterDialog)

}