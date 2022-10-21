package com.five.zensearch.domain.dto

import java.util.*

data class PostDTO(
    val title: String?,
    val name:String?,
    val address: String?,
    val description: String?,
    val tags: List<String>?,
    val image: String?,
    val isSubmitted: Boolean? = false,
    val date: Date?
)