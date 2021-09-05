package com.omoemurao.sopegina.data.api.response

import com.omoemurao.sopegina.data.model.GifNetwork
import kotlinx.serialization.Serializable

@Serializable
data class GifPageResponse(val result: List<GifNetwork>)