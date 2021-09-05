package com.omoemurao.sopegina

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

    private var type: GifTypes = GifTypes.RANDOM

    init {
        fetchUsers()
    }

    fun getPrevious() {
        fetchUsers()
    }

    fun getNext() {
        fetchUsers()
    }

    private fun fetchUsers() {
        viewModelScope.launch {
            gifCurrent.postValue(Resource.Loading())
            try {
                val usersFromApi = repository.getRandomGif()
                if(usersFromApi != null)
                gifCurrent.postValue(Resource.Success(usersFromApi))
            } catch (e: Exception) {
                gifCurrent.postValue(Resource.Error(e.toString(), null))
            }
        }
    }

    fun setType(type: GifTypes?) {
        this.type = type ?: GifTypes.RANDOM
    }
}