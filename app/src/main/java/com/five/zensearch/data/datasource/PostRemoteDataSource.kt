package com.five.zensearch.com.five.zensearch.data.datasource

import com.five.zensearch.com.five.zensearch.data.dto.PostDTO
import com.five.zensearch.com.five.zensearch.utils.Constants
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.runBlocking

class PostRemoteDataSource() {
    private val postsTableRef: DatabaseReference by lazy {
        FirebaseDatabase.getInstance(Constants.FIREBASE_DATABASE_URL)
            .reference.child(Constants.POSTS_TBL_REF)
    }

    fun createPost(postDTO: PostDTO) {
        val postId: String = postsTableRef.push().key ?: ""
        postsTableRef.child(postId).setValue(postDTO)
    }

    fun cancelPost(postId: String) {
        postsTableRef.child(postId).child(Constants.POSTS_CONFIRMED_FIELD_REF).setValue(false)
    }

    fun subscribeUser(userId: String, postId: String) {
        postsTableRef.child(postId).child(Constants.POSTS_PARTICIPANTS_FIELD_REF)
            .updateChildren(mapOf(userId to true))
    }

    fun unsubscribeUser(userId: String, postId: String) {
        postsTableRef.child(postId).child(Constants.POSTS_PARTICIPANTS_FIELD_REF).child(userId).removeValue()
    }

    fun getPosts(): Flow<List<PostDTO>> {
        val postsFlow: MutableSharedFlow<List<PostDTO>> = MutableSharedFlow()
        postsTableRef.addValueEventListener(object : ValueEventListener {
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

}