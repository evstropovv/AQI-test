package com.testtask2.data.repository

import com.nhaarman.mockitokotlin2.given
import com.testtask2.data.TestData
import com.testtask2.data.cache.DataSourceCacheImpl
import com.testtask2.data.mapper.DataMapper
import com.testtask2.data.remote.DataSourceRemoteImpl
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner.Silent::class)
class RepositoryImplTest {

    @Mock
    internal lateinit var dataSourceRemote: DataSourceRemoteImpl

    @Mock
    internal lateinit var dataSourceCache: DataSourceCacheImpl

    private lateinit var cut: RepositoryImpl
    private lateinit var mapper: DataMapper
    private val testDispatcher = TestCoroutineDispatcher()
    private val managedCoroutineScope = CoroutineScope(testDispatcher)

    @Before
    fun setUp() {
        cut = RepositoryImpl(dataSourceRemote, dataSourceCache)
        mapper = DataMapper()
    }

    @Test
    fun `getStationInfo return not null value if remote and cache data sources have values`() {
        runBlocking {

            given(dataSourceRemote.getStationInfo(1))
                .willReturn(mapper.toDomain(TestData.getStationInfoDto().data))
            given(dataSourceCache.getStationInfo(1))
                .willReturn(flow { emit(mapper.toDomain(TestData.getStationInfoDB())) })
            // when
            val result = cut.getStationInfo(1, managedCoroutineScope).first()

            assertNotNull(result)
        }
    }

}