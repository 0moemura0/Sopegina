package com.omoemurao.sopegina

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omoemurao.sopegina.data.api.request.GifPageRequest
import com.omoemurao.sopegina.data.model.GifNetwork
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: GifRepository) :
    ViewModel() {

    val gifCurrent = MutableLiveData<Resource<GifNetwork>>()

    private val currentPage = mutableListOf<GifNetwork>()
    private var type: GifTypes = GifTypes.RANDOM

    val gifQueueId = MutableLiveData(0)
    private val defaultPage = 5

    private fun fetchRandomGif() {
        viewModelScope.launch {
            gifCurrent.postValue(Resource.Loading())
            try {
                val usersFromApi = repository.getRandomGif()
                if (usersFromApi != null)
                    gifCurrent.postValue(Resource.Success(usersFromApi))
            } catch (e: Exception) {
                gifCurrent.postValue(Resource.Error(e.toString(), null))
            }
        }
    }

    private fun fetchTypedGif() {
        viewModelScope.launch {
            gifCurrent.postValue(Resource.Loading())
            try {
                val usersFromApi =
                    repository.getGifList(
                        GifPageRequest(
                            type.urlName,
                            ((gifQueueId.value ?: 0) / defaultPage)
                        )
                    )
                if (usersFromApi != null && !usersFromApi.result.isNullOrEmpty()) {
                    currentPage.addAll(usersFromApi.result)
                    gifCurrent.postValue(Resource.Success(currentPage[gifQueueId.value ?: 0]))
                } else gifCurrent.postValue(Resource.Error("Empty list"))
            } catch (e: Exception) {
                gifCurrent.postValue(Resource.Error(e.message.toString(), null))
            }
        }
    }

    fun getNextGif() {
        when (type) {
            GifTypes.RANDOM -> {
                fetchRandomGif()
            }
            else -> {
                if (((gifQueueId.value ?: 0) % defaultPage) == 0
                    && (gifQueueId.value ?: 0) >= currentPage.size
                ) {
                    fetchTypedGif()
                } else
                    getNextGifFromList()
            }
        }
    }

    private fun getNextGifFromList() {
        gifCurrent.value = Resource.Loading()
        val result = currentPage[gifQueueId.value ?: 0]
        if (result != null)
            gifCurrent.value = Resource.Success(result)
        else
            gifCurrent.value = Resource.Error("something wrong")
    }

    fun setType(_type: GifTypes?) {
        type = _type ?: GifTypes.RANDOM
        getNextGif()
    }
}