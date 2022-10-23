package com.five.zensearch.com.five.zensearch.data.dto

import java.util.*

data class PostDTO(
    val id: String?,
    val title:String?,
    val address: String?,
    val description: String?,
    val tags: List<String>?,
    val image: String?,
    val isConfirmed: Boolean? = false,
    val date: Date?,
    val creatorId: String?,
    val participants: List<String>?
)