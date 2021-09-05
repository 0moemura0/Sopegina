package com.omoemurao.sopegina.data.model
import kotlinx.serialization.Serializable

@Serializable
data class CommentNetwork(
    val id: Int,
    val text: String,
    val date: String,
    val voteCount: Int,
    val authorId: Int,
    val authorName: String,
    val deleted: Boolean,
    val voted: Boolean,
    val editable: Boolean
)