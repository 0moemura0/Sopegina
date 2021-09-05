package com.omoemurao.sopegina.data.api.response

import com.omoemurao.sopegina.data.model.CommentNetwork
import kotlinx.serialization.Serializable

@Serializable
data class CommentResponse(val comments: List<CommentNetwork>)