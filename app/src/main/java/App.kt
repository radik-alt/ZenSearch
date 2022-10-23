package com.five.zensearch

import android.app.Application
import android.content.Context
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                return@OnCompleteListener
            }
            token = task.result
        })
    }

    companion object {
        private lateinit var token: String
        fun getToken() = token
        private lateinit var context: Context
        fun getContext() = context
    }
}