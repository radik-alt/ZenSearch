package com.five.zensearch.com.five.zensearch.domain.repo

import com.five.zensearch.com.five.zensearch.domain.model.PostModel

interface PostRepo {
    fun createPost(post: PostModel)
    fun cancelPost(postId: String)
    fun subscribeUser(userId: String, postId: String)
    fun unsubscribeUser(userId: String, postId: String)
}