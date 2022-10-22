package com.five.zensearch.com.five.zensearch.presentation.registrationFragment

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.five.zensearch.App
import com.five.zensearch.com.five.zensearch.data.datasource.UserRemoteDataSource
import com.five.zensearch.com.five.zensearch.data.dto.UserDTO
import com.five.zensearch.com.five.zensearch.data.repo_impl.UserRepoImpl

class SingUpViewModel(

) :ViewModel() {

    private val userRepo = UserRepoImpl(UserRemoteDataSource())

    fun singUp(email:String, password:String, userDTO: UserDTO){
        userRepo.singUp(email, password, userDTO)
    }

    fun showToast(context: Context, error:String){
        Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
    }
}