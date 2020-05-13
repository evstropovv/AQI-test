package com.testtask2.di

import com.testtask2.data.cache.DataSourceCacheImpl
import com.testtask2.data.datasource.DataSourceCache
import com.testtask2.data.datasource.DataSourceRemote
import com.testtask2.data.remote.DataSourceRemoteImpl
import com.testtask2.data.repository.RepositoryImpl
import com.testtask2.domain.repository.Repository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class DataModule {

    @Binds
    @Singleton
    abstract fun provideRepository(repository: RepositoryImpl?): Repository?

    @Binds
    @Singleton
    abstract fun provideDataSourceCache(repository: DataSourceCacheImpl?): DataSourceCache?

    @Binds
    @Singleton
    abstract fun provideDataSourceRemote(repository: DataSourceRemoteImpl?): DataSourceRemote?

}
