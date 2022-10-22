package com.five.zensearch.com.five.zensearch.data.repo_impl

import com.five.zensearch.com.five.zensearch.data.datasource.PostRemoteDataSource
import com.five.zensearch.com.five.zensearch.data.toDTO
import com.five.zensearch.com.five.zensearch.domain.model.PostModel
import com.five.zensearch.com.five.zensearch.domain.repo.PostRepo


class PostRepoImpl(private val remoteDataSource: PostRemoteDataSource) : PostRepo {

    override fun createPost(post: PostModel) {
        remoteDataSource.createPost(post.toDTO())
    }

    override fun cancelPost(postId: String) {
        remoteDataSource.cancelPost(postId)
    }

    override fun subscribeUser(userId: String, postId: String) {
        remoteDataSource.subscribeUser(userId, postId)
    }

    override fun unsubscribeUser(userId: String, postId: String) {
        remoteDataSource.unsubscribeUser(userId, postId)
    }
}