package com.five.zensearch.com.five.zensearch.presentation.create_event

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.five.zensearch.com.five.zensearch.data.dto.PostDTO
import com.five.zensearch.com.five.zensearch.data.dto.UserDTO
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody

class CreateEventViewModel() : ViewModel() {
    //    fun sendMessage(post: PostDTO) {
//        sendMessage(post)
//            .onEach(_messageSend::setValue)
//            .launchIn(viewModelScope)
//        sendNotification(
//            sender = "${currentUser.value?.name} " + "${currentUser.value?.lastName}",
//            post
//        )
////        userInteractor.updateLastMessage(localOtherUserId, localCurrentUserId, message)
    private val database = Firebase.database
    private val usersReference = database.getReference("Users")
    private val auth: FirebaseAuth = Firebase.auth
    private val _user: MutableLiveData<FirebaseUser> = MutableLiveData()
    val user: LiveData<FirebaseUser> get() = _user
    private val _listUser: MutableLiveData<List<UserDTO>> = MutableLiveData()

    init {
        auth.addAuthStateListener {
            _user.value = it.currentUser
        }
    }


    private fun getUsers(currentUser: FirebaseUser): Flow<List<UserDTO>> {
        val usersFlow: MutableSharedFlow<List<UserDTO>> = MutableSharedFlow()
        val userFromDB = mutableListOf<UserDTO>()
        usersReference.get()
            .addOnSuccessListener {
                runBlocking {
                    it.children.forEach {
                        val user = it.getValue<UserDTO>()
                        if (!user?.id.equals(currentUser.uid))
                            user?.let { it1 -> userFromDB.add(it1) }

                    }
                    usersFlow.emit(userFromDB)
                }
            }
        return usersFlow
    }

    private fun sendNotification(post: PostDTO) {
        var users: List<UserDTO> = mutableListOf()
        auth.currentUser?.let { userDB ->
            getUsers(userDB)
                .onEach {
                    users = it
                }
                .launchIn(viewModelScope)
        }

        users.forEach {
            Thread {
                val client = OkHttpClient().newBuilder()
                    .build()
                val mediaType: MediaType = "application/json".toMediaTypeOrNull()!!
                val body: RequestBody = RequestBody.create(
                    mediaType,
                    content = "{\r\n    \"to\":\"${it.token}\"," +
                            "\r\n    \"notification\": {\r\n        " +
                            "\"title\": \"${post.title}\",\r\n        " +
                            "\"body\": \"Не забудьте про мероприятие!!!${post.date}\"\r\n    }\r\n}"
                )
                val request: Request = Request.Builder()
                    .url("https://fcm.googleapis.com/fcm/send")
                    .method("POST", body)
                    .addHeader(
                        "Authorization",
                        "key=${API_KEY}"
                    )
                    .addHeader("Content-Type", "application/json")
                    .build()
                client.newCall(request).execute()
            }.start()
        }
    }

    companion object {
        private const val API_KEY =
            "AAAAGal2HXI:APA91bFnviN_ofX7EMWWhsNim0SGaBnO5iFJ5F0zri0IqgTkCOpxLlxK9NkFFzKjec_dGUayDch6wunau06qiqB0DAl2FKQXadWqW1Kr-fofnMFATtI5Bpx1ZEAgqRmT1H-WBk97KpQ7"
    }

}