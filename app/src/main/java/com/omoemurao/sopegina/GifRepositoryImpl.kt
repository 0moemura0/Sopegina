package com.omoemurao.sopegina

import com.omoemurao.sopegina.Mapper.toEntity
import com.omoemurao.sopegina.data.api.request.GifPageRequest
import com.omoemurao.sopegina.data.api.ApiHelper
import com.omoemurao.sopegina.data.api.response.CommentResponse
import com.omoemurao.sopegina.data.api.response.GifPageResponse
import com.omoemurao.sopegina.data.local.DatabaseHelper
import com.omoemurao.sopegina.data.model.GifNetwork
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GifRepositoryImpl @Inject constructor(val apiService: ApiHelper, val dao: DatabaseHelper) :
    GifRepository {
    override suspend fun getGifList(pageRequest: GifPageRequest): GifPageResponse? =
        apiService.getPage(pageRequest)

    override suspend fun getGifComments(entryId: Int): CommentResponse? =
        apiService.getComments(entryId)

    override suspend fun getRandomGif(): GifNetwork? =
        apiService.getRandomGif()


    override suspend fun getGitByID(entryId: Int): Flow<Resource<GifNetwork>> {
        return flow {
            emit(Resource.Loading(null))
            val cached = dao.getGif(entryId)
            emit(Resource.Success(cached))

            if (cached == null) {
                val remote = apiService.getGifByID(entryId)
                if (remote != null) {
                    saveToLocal(remote)
                }
                emit(Resource.Success(remote))
            }
        } as Flow<Resource<GifNetwork>>
    }

    private suspend fun saveToLocal(remote: GifNetwork) {
        dao.insert(remote.toEntity(0))
    }

}
