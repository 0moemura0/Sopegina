package com.omoemurao.sopegina

import com.omoemurao.sopegina.data.api.request.GifPageRequest
import com.omoemurao.sopegina.data.api.response.CommentResponse
import com.omoemurao.sopegina.data.api.response.GifPageResponse
import com.omoemurao.sopegina.data.model.GifNetwork
import kotlinx.coroutines.flow.Flow

interface GifRepository {

    suspend fun getGifList(pageRequest: GifPageRequest) : GifPageResponse?

    suspend fun getGifComments(entryId: Int): CommentResponse?

    suspend fun getRandomGif(): GifNetwork?

    suspend fun getGitByID(entryId: Int) : Flow<Resource<GifNetwork>>
}
