package com.five.zensearch.com.five.zensearch.presentation.create_event

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.five.zensearch.com.five.zensearch.data.dto.PostDTO
import com.five.zensearch.com.five.zensearch.data.repo_impl.PostRepoImpl
import com.five.zensearch.com.five.zensearch.data.toDTO
import com.five.zensearch.com.five.zensearch.domain.model.PostModel
import com.five.zensearch.com.five.zensearch.presentation.create_event.tagsRecycler.TagsRecyclerView
import com.five.zensearch.databinding.FragmentCreateEventBinding
import com.five.zensearch.databinding.FragmentLogInBinding
import com.google.firebase.auth.FirebaseAuth
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.min

class CreateEventFragment : Fragment() {


    private val auth = FirebaseAuth.getInstance()

    private var _binding: FragmentCreateEventBinding?=null
    private val binding:FragmentCreateEventBinding
        get() = _binding ?: throw RuntimeException("FragmentCreateEventBinding == null")

    private val selectTags = ArrayList<String>()

    private val getTagsNames = ArrayList<String>()
    private val createViewModel : CreateViewModel by viewModels()
    private val calendar = Calendar.getInstance()
    private var longTime = 0L

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateEventBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onResume() {
        super.onResume()
        getTagsNames.clear()
        getTagsNames.add("Фильмы")
        getTagsNames.add("Культура")
        getTagsNames.add("Спорт")
        getTagsNames.add("Игры")
        getTagsNames.add("Наука")
        getTagsNames.add("Книги")
        getTagsNames.add("Природа")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.createEventCreate.setOnClickListener {
            createEvent()
        }

        binding.createEventDate.setOnClickListener {

        }

        binding.createEventTime.setOnClickListener {
            TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                val sumTime = hour+ minute
                longTime = sumTime.toLong()
                calendar.time.time = sumTime.toLong()
                Log.d("GetTime", longTime.toString())
            }
        }

    }

    private fun setAdapter(){
        val tagsAdapter = TagsRecyclerView(getTagsNames)
        binding.recyclerTags.adapter = tagsAdapter
        binding.recyclerTags.layoutManager = GridLayoutManager(requireContext(), 4)
    }

    private fun createEvent(){
        val name = binding.createEventName.text.toString()
        val desc = binding.createEventDescription.text.toString()
        val address = binding.address.text.toString()


        if (validEvent(name, address, desc)){
            val event = PostDTO(
                id = null,
                title = name,
                address = address,
                description = desc,
                tags = selectTags.toList(),
                image = "image",
                isConfirmed = true,
                date = calendar.time,
                creatorId = auth.uid.toString(), //auth.currentUser.uid
                participants = listOf()
            )
            createViewModel.createPost(event)
        } else {
            Toast.makeText(requireContext(), "Введите все поля", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {

    }

    private fun validEvent(title:String, address: String, description:String): Boolean {
        if (title.isNotEmpty() && address.isNotEmpty() && description.isNotEmpty()) return true
        return false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}