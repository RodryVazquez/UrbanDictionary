package com.example.urbandictionary.repositories

import com.example.urbandictionary.data.UrbanResponseData
import com.example.urbandictionary.utils.NetworkResult

interface UrbanDictionaryRepository {
    suspend fun searchUrbanItems(query: String): NetworkResult<UrbanResponseData>
}