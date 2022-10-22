package com.five.zensearch.presentation.homeFragment

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import com.five.zensearch.R
import com.five.zensearch.com.five.zensearch.presentation.homeFragment.EventsListRecyclerAdapter
import com.five.zensearch.com.five.zensearch.presentation.homeFragment.HomeViewModel
import com.five.zensearch.databinding.FragmentHomeBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.*

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val homeViewModel: HomeViewModel by viewModels<HomeViewModel>()

    override fun onResume() {
        super.onResume()
        showBottomView()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeViewModel.getUser()
        homeViewModel.currentUser.observe(viewLifecycleOwner) {
            homeViewModel.generatePostsList()
            homeViewModel.recommendedEventsList.observe(viewLifecycleOwner) { postsList ->
                val adapter = EventsListRecyclerAdapter(homeViewModel::subscribeTo, homeViewModel::unsubscribeTo)
                binding.postsList.adapter = adapter
                adapter.submitList(postsList)
            }
        }
    }

    private fun showBottomView(){
        val fragmentActivity = activity
        if (activity != null){
            val bottom = fragmentActivity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)
            if (bottom != null && bottom.visibility == View.GONE) {
                bottom.visibility = View.VISIBLE
            }
        }
    }

}