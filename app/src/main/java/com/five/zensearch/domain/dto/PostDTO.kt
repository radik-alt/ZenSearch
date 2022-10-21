package com.five.zensearch.domain.dto

data class PostDTO(
    val title: String?,
    val name:String?,
    val address: String?,
    val description: String?,
    val tags: List<String>?,
    val image: String?
)