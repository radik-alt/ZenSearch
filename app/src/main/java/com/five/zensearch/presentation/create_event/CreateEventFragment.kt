package com.five.zensearch.com.five.zensearch.presentation.create_event

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.five.zensearch.com.five.zensearch.domain.model.PostModel
import com.five.zensearch.databinding.FragmentCreateEventBinding
import java.util.*

class CreateEventFragment : Fragment() {


    private lateinit var binding: FragmentCreateEventBinding
//    private val createViewModel : CreateViewModel by lazy{
//        ViewModelProvider(this)[CreateViewModel::class.java]
//    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateEventBinding.inflate(inflater, container, false)
        return binding.root

        
    }

    private fun createEvent(){
        val event = PostModel(
            title = "title",
            address = "address",
            description = "description",
            tags = listOf("tags"),
            image = "image",
            isConfirmed = true,
            date = Date(),
            creatorId = "EcvBeMsL37W9QRhW3F9fHjAQNtl2" //auth.currentUser.uid
        )
//        createViewModel.createPost(event)
    }

    private fun validEvent(title:String, address: String, description:String): Boolean {
        if (title.isNotEmpty() && address.isNotEmpty() && description.isNotEmpty()) return true
        return false
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}