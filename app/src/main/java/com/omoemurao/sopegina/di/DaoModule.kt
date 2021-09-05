package com.omoemurao.sopegina.di

import com.omoemurao.sopegina.data.local.AppDatabase
import com.omoemurao.sopegina.data.local.dao.GifDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
class DaoModule {
    @Provides
    fun providesGifDao(database: AppDatabase): GifDao = database.gifDao()

}