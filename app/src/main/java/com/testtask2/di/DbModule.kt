package com.testtask2.di

import android.content.Context
import androidx.room.Room
import com.testtask2.data.cache.db.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DbModule {

    @Provides
    @Singleton
    fun appDb(context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "stations")
            .build()
    }
}