package com.omoemurao.sopegina.data.api

import com.omoemurao.sopegina.data.api.request.GifPageRequest
import com.omoemurao.sopegina.data.api.response.CommentResponse
import com.omoemurao.sopegina.data.api.response.GifPageResponse
import com.omoemurao.sopegina.data.model.GifNetwork
import javax.inject.Inject


class ApiHelperImpl @Inject constructor(private val apiService: ApiService) : ApiHelper {

    override suspend fun getPage(pageRequest: GifPageRequest): GifPageResponse? =
        apiService.getPageByType(pageRequest.type, pageRequest.page)


    override suspend fun getRandomGif(): GifNetwork? =
        apiService.getGIFRandom()


    override suspend fun getGifByID(id: Int): GifNetwork? =
        apiService.getGIFCurrent(id)


    override suspend fun getComments(entryId: Int): CommentResponse? =
        apiService.getComments(entryId)


}