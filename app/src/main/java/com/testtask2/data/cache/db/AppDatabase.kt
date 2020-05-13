package com.testtask2.data.cache.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(StationsDB::class, StationInfoDB::class), version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun stations(): StationsDao

    abstract fun stationInfo(): StationInfoDao
}
