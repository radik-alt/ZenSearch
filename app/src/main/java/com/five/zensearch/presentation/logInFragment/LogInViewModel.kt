package com.five.zensearch.com.five.zensearch.presentation.logInFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.five.zensearch.App.Companion.getToken
import com.five.zensearch.com.five.zensearch.data.datasource.UserRemoteDataSource
import com.five.zensearch.com.five.zensearch.data.dto.UserDTO
import com.five.zensearch.com.five.zensearch.data.repo_impl.UserRepoImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
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

class LogInViewModel: ViewModel() {

    private var auth: FirebaseAuth = Firebase.auth
    private val _error: MutableLiveData<String> = MutableLiveData()
    val error: LiveData<String> get() = _error
    private val _user: MutableLiveData<FirebaseUser> = MutableLiveData()
    val user: LiveData<FirebaseUser> get() = _user
    private val database = Firebase.database
    private val usersReference = database.getReference("Users")
    private val localUser = MutableLiveData<UserDTO?>()

    init {
        auth.addAuthStateListener {
            _user.value = it.currentUser
        }
        user.value?.let {
            getCurrentUsers(it.uid)
                .onEach(localUser::setValue)
                .launchIn(viewModelScope)
        }
    }

    fun refreshToken() {
        if (localUser.value?.token != getToken()) {
            val token: HashMap<String, Any> = HashMap()
            token["token"] = getToken()
            user.value?.let { updateToken(it.uid, token) }
        }
    }

    fun logIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).addOnFailureListener {
            _error.value = it.message
        }

        refreshToken()
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
    fun updateToken(currentUserId: String, token: HashMap<String, Any>) {
        usersReference.child(currentUserId).updateChildren(token)
    }

}