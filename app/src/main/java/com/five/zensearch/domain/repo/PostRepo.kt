package com.five.zensearch.com.five.zensearch.domain.repo

import com.five.zensearch.com.five.zensearch.data.dto.PostDTO
import kotlinx.coroutines.flow.Flow

interface PostRepo {
    fun createPost(post: PostDTO)
    fun cancelPost(postId: String)
    fun subscribeUser(userId: String, postId: String)
    fun unsubscribeUser(userId: String, postId: String)
    fun getPosts(): Flow<List<PostDTO>>
}