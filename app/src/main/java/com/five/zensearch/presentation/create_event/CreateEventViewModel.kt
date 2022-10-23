package com.five.zensearch.com.five.zensearch.presentation.create_event

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.five.zensearch.com.five.zensearch.data.dto.PostDTO
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
//    }

    private fun sendNotification(token:, post: PostDTO) {
        Thread {
            val client = OkHttpClient().newBuilder()
                .build()
            val mediaType: MediaType = "application/json".toMediaTypeOrNull()!!
            val body: RequestBody = RequestBody.create(
                mediaType,
                content = "{\r\n    \"to\":\"${otherUser.value?.token}\"," +
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

    companion object {
        private const val API_KEY =
            "AAAAXB36bKU:APA91bF_PC-_TKH6cVw8dvv-0By7QQ1DPYOm25o_m0Ma4pPBFffZpTx7Ior2FDAGchlLMlIbhhLZgfG-6TypiGsgbra7sAbHaIj-MAKkCK7a1dXDFWhEgxaCaQYXT8fuvQE31HGBhRSM"
    }

}