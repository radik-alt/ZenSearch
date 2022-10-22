package com.five.zensearch.com.five.zensearch.domain.repo

interface UserRepo {
    fun authUser(userEmail: String, password:String)
    fun singUp(userEmail: String, password: String)
}