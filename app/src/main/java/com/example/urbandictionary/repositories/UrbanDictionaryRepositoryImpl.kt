package com.example.urbandictionary.repositories

import com.example.urbandictionary.data.UrbanResponseData
import com.example.urbandictionary.utils.NetworkResult
import com.example.urbandictionary.utils.safeApiCall
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

class UrbanDictionaryRepositoryImpl(
    private val urbanDictionaryService: UrbanDictionaryService,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : UrbanDictionaryRepository {
    override suspend fun searchUrbanItems(query: String): NetworkResult<UrbanResponseData> {
        return safeApiCall(dispatcher) {
            urbanDictionaryService.searchUrbanItems(query)
        }
    }
}

interface UrbanDictionaryService {

    @Headers("Content-Type: application/json")
    @GET("/define")
    suspend fun searchUrbanItems(@Query("term") term: String): UrbanResponseData
}