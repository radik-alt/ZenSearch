package com.five.zensearch.com.five.zensearch.data.repo_impl

import com.five.zensearch.com.five.zensearch.data.datasource.PostRemoteDataSource
import com.five.zensearch.com.five.zensearch.data.dto.PostDTO
import com.five.zensearch.com.five.zensearch.domain.repo.PostRepo
import kotlinx.coroutines.flow.Flow


class PostRepoImpl(private val remoteDataSource: PostRemoteDataSource) : PostRepo {

    override fun createPost(post: PostDTO) {
        remoteDataSource.createPost(post)
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

    override fun getPosts(): Flow<List<PostDTO>> = remoteDataSource.getPosts()

}