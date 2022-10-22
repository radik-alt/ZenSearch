package com.five.zensearch.com.five.zensearch.data.repo_impl

import com.five.zensearch.com.five.zensearch.data.datasource.UserRemoteDataSource
import com.five.zensearch.com.five.zensearch.domain.repo.UserRepo

class UserRepoImpl(private val remoteDataSource: UserRemoteDataSource) : UserRepo {

}