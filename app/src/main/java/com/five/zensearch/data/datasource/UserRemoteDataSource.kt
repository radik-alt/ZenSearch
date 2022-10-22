package com.five.zensearch.com.five.zensearch.data.datasource

import com.google.firebase.auth.FirebaseAuth

class UserRemoteDataSource() {

    private val auth = FirebaseAuth.getInstance()

    fun authUser(email:String, password:String){
        val requset = auth.signInWithEmailAndPassword(email, password)
        if (requset.isSuccessful){
            TODO("Реализовать логику успешной авториазции")
        } else {
            TODO("Реализовать логику ошибочной авториазции")
        }
    }

    fun singUp(email:String, password:String){
        val request = auth.createUserWithEmailAndPassword(email, password)
        if (request.isSuccessful){
            TODO("Реализовать логику успешной регистрации")
        } else {
            TODO("Реализовать логику успешной регистрации")
        }
    }

}