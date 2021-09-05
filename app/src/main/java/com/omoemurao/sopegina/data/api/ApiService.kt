package com.omoemurao.sopegina.data.api

import com.omoemurao.sopegina.data.api.response.CommentResponse
import com.omoemurao.sopegina.data.model.GifNetwork
import com.omoemurao.sopegina.data.api.response.GifPageResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("/{type}/{page}?json=true")
    suspend fun getPageByType(@Path("type") type: String, @Path("page") page: Int): GifPageResponse?

    @GET("/random?json=true")
    suspend fun getGIFRandom(): GifNetwork?

    @GET("/{id}?json=true")
    suspend fun getGIFCurrent(@Path("id") id: Int): GifNetwork?

    @GET("/comments/entry/{id}")
    suspend fun getComments(@Path("id") id: Int): CommentResponse?
}