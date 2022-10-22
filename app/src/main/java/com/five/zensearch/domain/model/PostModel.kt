package com.five.zensearch.com.five.zensearch.domain.model

import java.util.*

data class PostModel (
    val id: String? = null,
    val title: String?,
    val name:String?,
    val address: String?,
    val description: String?,
    val tags: List<String>?,
    val image: String?,
    val isConfirmed: Boolean? = false,
    val date: Date?,
    val creatorId: String? = null,
    val participants: List<String>? = null
)