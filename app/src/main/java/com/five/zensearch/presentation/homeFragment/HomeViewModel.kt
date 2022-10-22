package com.five.zensearch.com.five.zensearch.presentation.homeFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.five.zensearch.com.five.zensearch.data.datasource.PostRemoteDataSource
import com.five.zensearch.com.five.zensearch.data.repo_impl.PostRepoImpl
import com.five.zensearch.com.five.zensearch.domain.model.PostModel
import com.google.firebase.auth.FirebaseAuth

class HomeViewModel() : ViewModel() {
    private val postRepo = PostRepoImpl(PostRemoteDataSource())
    private val user = FirebaseAuth.getInstance().currentUser

    private var _eventsList: MutableLiveData<List<PostModel>> = MutableLiveData(mutableListOf())
    val eventsList: LiveData<List<PostModel>>
        get() = _eventsList

    fun subscribeTo(eventId: String) {
        //postRepo.subscribeUser(user!!.uid, eventId)
        postRepo.subscribeUser("EcvBeMsL37W9QRhW3F9fHjAQNtl2", eventId)
    }

    fun unsubscribeTo(eventId: String) {
        //postRepo.unsubscribeUser(user!!.uid, eventId)
        postRepo.unsubscribeUser("EcvBeMsL37W9QRhW3F9fHjAQNtl2", eventId)
    }
}