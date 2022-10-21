package com.five.zensearch.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.lifecycle.MutableLiveData
import com.five.zensearch.App.Companion.getToken
import com.five.zensearch.R
import com.five.zensearch.domain.dto.UserDTO
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    private var auth: FirebaseAuth = Firebase.auth

    val error: MutableLiveData<String> = MutableLiveData()
    private val database = Firebase.database
    private val usersReference = database.getReference("Users")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navController = Navigation.findNavController(this, R.id.nav_host)
//        createUser()
    }


    /**
    CREATE USER
     **/
    fun createUser() {
        auth.createUserWithEmailAndPassword("v@emiryan.ru", "qwerty").addOnSuccessListener {
                val user =
                    UserDTO(it.user?.uid, "Vova", "Emiryan", "Hach", false, getToken(), "img")
                usersReference.child(user.id.toString()).setValue(user)
            }.addOnFailureListener {
                error.value = it.message
            }
    }
}
