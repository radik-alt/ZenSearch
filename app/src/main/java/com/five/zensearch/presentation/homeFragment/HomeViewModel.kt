package com.five.zensearch.com.five.zensearch.presentation.homeFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.five.zensearch.com.five.zensearch.data.dto.PostDTO
import com.five.zensearch.com.five.zensearch.data.dto.UserDTO
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking

class HomeViewModel : ViewModel() {
    private val _postsLiveData = MutableLiveData<List<PostDTO>>()
    val postsLiveData: LiveData<List<PostDTO>>
        get() = _postsLiveData
    private val _otherUser = MutableLiveData<UserDTO?>()
    val otherUser: LiveData<UserDTO?>
        get() = _otherUser
    private val _currentUser = MutableLiveData<UserDTO?>()
    private val currentUser: LiveData<UserDTO?>
        get() = _currentUser
    private val _postSend = MutableLiveData<Boolean>()
    val postSend: LiveData<Boolean>
        get() = _postSend
    private val _error = MutableLiveData<String>()
    val error: MutableLiveData<String>
        get() = _error

    private val database = Firebase.database
    private val postsReference = database.getReference("Posts")
    private val usersReference = database.getReference("Users")

    private val localCurrentUserId = currentUserId

    init {
        getCurrentUsers(currentUserId)
            .onEach(_currentUser::setValue)
            .launchIn(viewModelScope)

        observePosts(currentUserId)
            .onEach(_postsLiveData::setValue)
            .launchIn(viewModelScope)
    }

    fun getCurrentUsers(currentUserId: String): Flow<UserDTO> {
        val currentUserFlow: MutableSharedFlow<UserDTO> = MutableSharedFlow()
        usersReference.child(currentUserId)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    runBlocking {
                        val user = snapshot.getValue<UserDTO>()
                        user?.let { currentUserFlow.emit(it) }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                }

            })
        return currentUserFlow
    }

    private fun observePosts(
        currentUserId: String
    ): Flow<List<PostDTO>> {
        val postsFlow: MutableSharedFlow<List<PostDTO>> = MutableSharedFlow()
        postsReference.child(currentUserId)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    runBlocking {
                        val posts: MutableList<PostDTO> = mutableListOf()
                        snapshot.children.forEach {
                            it.getValue<PostDTO>()?.let { post -> posts.add(post) }
                        }
                        postsFlow.emit(posts)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                }

            })
        return postsFlow
    }

    companion object {
        private const val API_KEY =
            "AAAAXB36bKU:APA91bF_PC-_TKH6cVw8dvv-0By7QQ1DPYOm25o_m0Ma4pPBFffZpTx7Ior2FDAGchlLMlIbhhLZgfG-6TypiGsgbra7sAbHaIj-MAKkCK7a1dXDFWhEgxaCaQYXT8fuvQE31HGBhRSM"
    }
}
