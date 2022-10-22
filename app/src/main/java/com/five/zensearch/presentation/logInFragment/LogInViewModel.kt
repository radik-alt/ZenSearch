package com.five.zensearch.com.five.zensearch.presentation.logInFragment

import androidx.lifecycle.ViewModel
import com.five.zensearch.com.five.zensearch.data.datasource.UserRemoteDataSource
import com.five.zensearch.com.five.zensearch.data.repo_impl.UserRepoImpl

class LogInViewModel: ViewModel() {

    private val userRepo = UserRepoImpl(UserRemoteDataSource())

    fun onLogInButtonPressed(email:String, password:String) {
        userRepo.authUser(email, password)
    }

}