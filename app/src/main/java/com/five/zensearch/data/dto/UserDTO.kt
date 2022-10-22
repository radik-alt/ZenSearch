package com.five.zensearch.com.five.zensearch.data.dto

data class UserDTO(
    val id: String?,
    val name: String?,
    val surname: String?,
    val age: Int?,
    val address: String?,
    val premium: Boolean?,
    val token: String?,
    val image: String?,
    val tags: List<String>? = null,
)

