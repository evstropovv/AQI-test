package com.testtask2.data.cache

import com.testtask2.data.TestData
import com.testtask2.data.cache.db.AppDatabase
import com.testtask2.data.cache.db.StationsDB
import com.testtask2.data.cache.db.StationsDao
import com.testtask2.data.mapper.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner.Silent::class)
class DataSourceCacheImplTest {

    private lateinit var cut: DataSourceCacheImpl

    @Mock
    lateinit var db: AppDatabase
    private lateinit var mapper: DataMapper

    @Before
    fun setUp() {
        mapper = DataMapper()
        cut = DataSourceCacheImpl(db, mapper)
    }

    @Test
    fun `getStations return not null data when db has values`() {
        runBlocking {
            given(db.stations()).willReturn(object : StationsDao {
                override fun getStations(minAqi: Int): Flow<List<StationsDB>> {
                    return flow {
                        emit(TestData.getStationDbList())
                    }
                }

                override suspend fun insert(employee: List<StationsDB>) {  }

                override fun clearTable() {}

            })

            val station = cut.getStationList(1).first()
            assertNotNull(station)
            assert(station.isNotEmpty())
        }
    }
}