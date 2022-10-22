package com.five.zensearch.com.five.zensearch.data.datasource

import com.five.zensearch.com.five.zensearch.data.dto.UserDTO
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.runBlocking

class UserRemoteDataSource {

    private val auth = FirebaseAuth.getInstance()
    private val usersReference = Firebase.database.getReference("Users")

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

    fun singUp(user: UserDTO) {
        usersReference.child(user.id.toString()).setValue(user)
    }

}