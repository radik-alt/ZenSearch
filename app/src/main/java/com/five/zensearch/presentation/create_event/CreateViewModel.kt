package com.five.zensearch.com.five.zensearch.presentation.create_event

import androidx.lifecycle.ViewModel
import com.five.zensearch.com.five.zensearch.data.datasource.PostRemoteDataSource
import com.five.zensearch.com.five.zensearch.data.dto.PostDTO
import com.five.zensearch.com.five.zensearch.data.repo_impl.PostRepoImpl
import java.util.*

class CreateViewModel(

) : ViewModel(){

    private val repo = PostRepoImpl(PostRemoteDataSource())

    fun createPost(post: PostDTO) {
        repo.createPost(post)
    }

}