package com.five.zensearch.com.five.zensearch.domain.repo

import com.five.zensearch.com.five.zensearch.data.dto.UserDTO
import kotlinx.coroutines.flow.Flow

interface UserRepo {
    fun singUp(user: UserDTO)
    fun getCurrentUser(id: String): Flow<UserDTO>
}