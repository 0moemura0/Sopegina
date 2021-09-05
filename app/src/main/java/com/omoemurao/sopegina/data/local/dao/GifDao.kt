package com.omoemurao.sopegina.data.local.dao

import androidx.room.*
import com.omoemurao.sopegina.data.local.entity.GifEntity

@Dao
interface GifDao {

    @Query("SELECT * FROM gifEntity")
    suspend fun getAll(): List<GifEntity>?

    @Query("SELECT * FROM gifEntity WHERE id = :id")
    suspend fun get(id: Int): GifEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users: List<GifEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: GifEntity)

    @Delete
    suspend fun delete(user: GifEntity)

}