package com.testtask2.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.testtask2.presentation.stations.StationsViewModel
import com.testtask2.presentation.userinfo.StationInfoViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(StationsViewModel::class)
    abstract fun bindPostsViewModel(viewModel: StationsViewModel?): ViewModel?

    @Binds
    @IntoMap
    @ViewModelKey(StationInfoViewModel::class)
    abstract fun bindUserInfoViewModel(viewModel: StationInfoViewModel?): ViewModel?

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory?): ViewModelProvider.Factory?

}