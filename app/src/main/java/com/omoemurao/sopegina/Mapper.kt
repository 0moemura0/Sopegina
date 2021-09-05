package com.omoemurao.sopegina

import com.omoemurao.sopegina.data.local.entity.GifEntity
import com.omoemurao.sopegina.data.model.GifNetwork

object Mapper {
    fun GifNetwork.toEntity(idQueue: Int): GifEntity = GifEntity(
        id = id ?: 0,
        idQueue = idQueue,
        description = description ?: "",
        votes = votes ?: 0,
        author = author ?: "",
        date = date ?: "",
        gifURL = gifURL ?: "",
        previewURL = previewURL ?: "",
        type = type ?: "",
        commentsCount = commentsCount ?: 0
    )
}