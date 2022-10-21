package com.example.zensearch.domain.dto

data class UserDTO (
    val name: String?,
    val surname: String?,
    val email: String?,
    val address: String?,
    val premium: Boolean?,
    val token: String?,
    val image: String?
)

