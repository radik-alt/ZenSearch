package com.five.zensearch.com.five.zensearch.presentation.homeFragment

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.five.zensearch.com.five.zensearch.data.datasource.PostRemoteDataSource
import com.five.zensearch.com.five.zensearch.data.datasource.Tags
import com.five.zensearch.com.five.zensearch.data.datasource.UserRemoteDataSource
import com.five.zensearch.com.five.zensearch.data.dto.PostDTO
import com.five.zensearch.com.five.zensearch.data.dto.UserDTO
import com.five.zensearch.com.five.zensearch.data.repo_impl.PostRepoImpl
import com.five.zensearch.com.five.zensearch.data.repo_impl.UserRepoImpl
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class HomeViewModel : ViewModel() {
    private val postRepo = PostRepoImpl(PostRemoteDataSource())
    private val userRepo = UserRepoImpl(UserRemoteDataSource())
    private val user = FirebaseAuth.getInstance().currentUser

    private var eventsList = ArrayList<PostDTO>()

    private var _recommendedEventsList: MutableLiveData<List<PostDTO>> = MutableLiveData(mutableListOf())
    val recommendedEventsList: LiveData<List<PostDTO>> = _recommendedEventsList

    private var _currentUser: MutableLiveData<UserDTO> = MutableLiveData()
    val currentUser: LiveData<UserDTO> = _currentUser

    fun getUser() {
        user?.uid?.let {
            userRepo.getCurrentUser(it)
                .onEach(_currentUser::setValue)
                .launchIn(viewModelScope)
        }//todo не уверен, что это то id
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun generatePostsList() {
        postRepo.getPosts()
            .onEach {
                eventsList = it as ArrayList<PostDTO>
            }
            .launchIn(viewModelScope)
        val userTags = listOf(Tags.Science, Tags.Books)
        val eventsPriority = ArrayList<Pair<Int, PostDTO>>()
        eventsList.forEach {
            val postTags = it.tags ?: listOf()
            var postPriorityValue = 0
            postTags.forEach { postTag ->
                userTags.forEach { userTag ->
                    if (userTag == postTag) {
                        postPriorityValue += 4
                    } else {
                        userTag.similar.forEach { similarToUserTag ->
                            if (postTag == similarToUserTag) {
                                postPriorityValue++
                            }
                        }
                    }
                }
            }
            eventsPriority.add(Pair(postPriorityValue, it))
        }
        eventsPriority.removeIf { it.first == 0 }
        eventsPriority.sortBy { it.first }
        eventsList = ArrayList()
        eventsPriority.forEach {
            eventsList.add(it.second)
        }
        _recommendedEventsList.value = eventsList
    }

    fun subscribeTo(eventId: String) {
        user?.let { postRepo.subscribeUser(it.uid, eventId) }
        //postRepo.subscribeUser("EcvBeMsL37W9QRhW3F9fHjAQNtl2", eventId)
    }

    fun unsubscribeTo(eventId: String) {
        user?.let { postRepo.unsubscribeUser(it.uid, eventId) }
        //postRepo.unsubscribeUser("EcvBeMsL37W9QRhW3F9fHjAQNtl2", eventId)
    }
}