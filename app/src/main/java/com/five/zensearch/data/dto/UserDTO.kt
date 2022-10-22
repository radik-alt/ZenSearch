package com.five.zensearch.com.five.zensearch.data.dto

import com.five.zensearch.com.five.zensearch.data.datasource.Tags

data class UserDTO(
    val id: String?,
    val name: String?,
    val surname: String?,
    val age: Int?,
    val address: String?,
    val premium: Boolean?,
    val token: String?,
    val image: String?,
    val tags: List<Tags>? = null,
)

