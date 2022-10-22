package com.five.zensearch.com.five.zensearch.data.repo_impl

import com.five.zensearch.com.five.zensearch.data.datasource.UserRemoteDataSource
import com.five.zensearch.com.five.zensearch.domain.repo.UserRepo

class UserRepoImpl(private val remoteDataSource: UserRemoteDataSource) : UserRepo {

    override fun authUser(userEmail: String, password: String) {
        remoteDataSource
    }

    override fun singUp(userEmail: String, password: String) {
        TODO("Not yet implemented")
    }

}