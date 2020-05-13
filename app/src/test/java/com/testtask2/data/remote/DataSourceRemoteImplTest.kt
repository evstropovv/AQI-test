package com.testtask2.data.remote

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.given
import com.testtask2.data.ApiRetrofit
import com.testtask2.data.TestData
import com.testtask2.data.mapper.DataMapper
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner.Silent::class)
class DataSourceRemoteImplTest {

    @Mock
    internal lateinit var apiRetrofit: ApiRetrofit

    private lateinit var cut: DataSourceRemoteImpl
    private lateinit var mapper: DataMapper

    @Before
    fun setUp() {
        mapper = DataMapper()
        cut = DataSourceRemoteImpl(apiRetrofit, mapper)
    }

    @Test
    fun `getStations return mapped data from remote data source`() {
        runBlocking {

            val answer = TestData.StationListDto()

            given(apiRetrofit.getStationListByCoordinates(any()))
                .willReturn(answer)


            val posts = cut.getStations("")

            val mappedPosts = TestData.StationListDto().data?.map{
                mapper.toDomain(it)
            }

            assert(posts?.isNotEmpty()!!)
            assertEquals(posts, mappedPosts)
        }
    }

}