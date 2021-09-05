package com.omoemurao.sopegina.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.omoemurao.sopegina.data.local.dao.GifDao
import com.omoemurao.sopegina.data.local.entity.GifEntity

@Database(entities = [GifEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun gifDao(): GifDao

}