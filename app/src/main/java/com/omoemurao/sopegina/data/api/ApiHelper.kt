package com.omoemurao.sopegina.data.api

import com.omoemurao.sopegina.data.api.request.GifPageRequest
import com.omoemurao.sopegina.data.api.response.CommentResponse
import com.omoemurao.sopegina.data.api.response.GifPageResponse
import com.omoemurao.sopegina.data.model.GifNetwork

interface ApiHelper {
    suspend fun getPage(pageRequest: GifPageRequest): GifPageResponse?

    suspend fun getRandomGif(): GifNetwork?

    suspend fun getGifByID(id: Int): GifNetwork?

    suspend fun getComments(entryId: Int): CommentResponse?
}
