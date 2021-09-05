package com.omoemurao.sopegina.data.api.request
import kotlinx.serialization.Serializable

@Serializable
data class GifPageRequest (val type: String, val page: Int)