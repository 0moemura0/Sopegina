package com.omoemurao.sopegina.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class GifEntity(
    @PrimaryKey val id: Int,
    val idQueue: Int,
    val description: String,
    val votes: Int,
    val author: String,
    val date: String,
    val gifURL: String,
    val previewURL: String,
    val type: String,
    val commentsCount: Int
)