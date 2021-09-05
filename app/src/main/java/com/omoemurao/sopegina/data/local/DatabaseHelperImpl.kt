package com.omoemurao.sopegina.data.local

import com.omoemurao.sopegina.data.local.entity.GifEntity
import javax.inject.Inject

class DatabaseHelperImpl @Inject constructor(private val appDatabase: AppDatabase) :
    DatabaseHelper {

    override suspend fun getGifs(): List<GifEntity>?
    = appDatabase.gifDao().getAll()

    override suspend fun getGif(id: Int): GifEntity?
    = appDatabase.gifDao().get(id)

    override suspend fun insertAll(gifs: List<GifEntity>)
    = appDatabase.gifDao().insertAll(gifs)

    override suspend fun insert(gif: GifEntity)
    = appDatabase.gifDao().insert(gif)

}