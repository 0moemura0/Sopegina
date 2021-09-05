package com.omoemurao.sopegina

import com.omoemurao.sopegina.data.local.entity.GifEntity
import com.omoemurao.sopegina.data.model.GifNetwork

object Mapper {
    fun GifNetwork.toEntity(idQueue: Int): GifEntity = GifEntity(
        id = id,
        idQueue = idQueue,
        description = description,
        votes = votes,
        author = author,
        date = date,
        gifURL = gifURL,
        previewURL = previewURL,
        type = type,
        commentsCount = commentsCount
    )
}