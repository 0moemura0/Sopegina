package com.omoemurao.sopegina.di

import android.content.Context
import androidx.room.Room
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.omoemurao.sopegina.GifRepository
import com.omoemurao.sopegina.GifRepositoryImpl
import com.omoemurao.sopegina.NetworkInterceptor
import com.omoemurao.sopegina.data.api.ApiHelper
import com.omoemurao.sopegina.data.api.ApiHelperImpl
import com.omoemurao.sopegina.data.api.ApiService
import com.omoemurao.sopegina.data.local.AppDatabase
import com.omoemurao.sopegina.data.local.DatabaseHelper
import com.omoemurao.sopegina.data.local.DatabaseHelperImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class SingletonModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(networkInterceptor: NetworkInterceptor) = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .addInterceptor(networkInterceptor)
        .connectTimeout(20, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()


    private val json = Json { ignoreUnknownKeys = true }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, BASE_URL: String): Retrofit =
        Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(
                @OptIn(ExperimentalSerializationApi::class)
                json.asConverterFactory("application/json".toMediaType())
            )
            .baseUrl(BASE_URL)
            .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "developers_life"
        ).build()

    @Provides
    fun provideInterceptor(@ApplicationContext context: Context): NetworkInterceptor {
        return NetworkInterceptor(context)
    }

    @Provides
    @Singleton
    fun provideBaseUrl() = "https://developerslife.ru/"

    @Provides
    @Singleton
    fun providesApiHelper(apiService: ApiService): ApiHelper = ApiHelperImpl(apiService)

    @Provides
    @Singleton
    fun providesDatabaseHelper(appDatabase: AppDatabase): DatabaseHelper =
        DatabaseHelperImpl(appDatabase)

    @Provides
    fun provideGifRepository(
        api: ApiHelper,
        database: DatabaseHelper
    ): GifRepository = GifRepositoryImpl(api, database)

}