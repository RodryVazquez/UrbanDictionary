package com.example.urbandictionary.view

import android.content.Context
import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.urbandictionary.data.UrbanResponseData
import com.example.urbandictionary.repositories.UrbanDictionaryRepository
import com.example.urbandictionary.utils.NetworkResult
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch

class ListViewModel @ViewModelInject constructor(
    @ApplicationContext val application: Context,
    private val urbanDictionaryRepository: UrbanDictionaryRepository
) : ViewModel() {

    val itemList = MutableLiveData<List<ItemCard>>()
    val loadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    fun searchUrbanItems(query: String) {
        loading.value = true
        viewModelScope.launch {
            when (val response = urbanDictionaryRepository.searchUrbanItems(query)) {
                is NetworkResult.Success -> {
                    loading.value = false
                    loadError.value = false
                    processNetworkResponse(response.value)
                }

                is NetworkResult.GenericError -> {
                    loading.value = false
                    loadError.value = true
                    response.error?.message?.let { Log.e("ListViewModel", it) }
                }

                is NetworkResult.NetworkError -> {
                    loading.value = false
                    loadError.value = true
                    Log.e("ListViewModl", "Network Error")
                }
            }
        }
    }

    private fun processNetworkResponse(response: UrbanResponseData) {
        val itemCardList = mutableListOf<ItemCard>()
        response.list.forEach { urbanItemResult ->
            itemCardList.add(
                ItemCard(
                    urbanItemResult.defid,
                    urbanItemResult.word,
                    urbanItemResult.definition,
                    urbanItemResult.permalink,
                    urbanItemResult.author,
                    urbanItemResult.example,
                    urbanItemResult.written_on,
                    urbanItemResult.thumbs_up,
                    urbanItemResult.thumbs_down
                )
            )
        }
        itemList.value = itemCardList
    }

    override fun onCleared() {
        super.onCleared()
        loading.value = true
    }
}