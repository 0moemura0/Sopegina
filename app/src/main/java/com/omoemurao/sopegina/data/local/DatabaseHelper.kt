package com.omoemurao.sopegina.data.local

import com.omoemurao.sopegina.data.local.entity.GifEntity

interface DatabaseHelper {

    suspend fun getGifs(): List<GifEntity>?

    suspend fun getGif(id: Int): GifEntity?

    suspend fun insertAll(gifs: List<GifEntity>)

    suspend fun insert(gif: GifEntity)

}