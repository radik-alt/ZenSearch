package com.five.zensearch.presentation.logInFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.five.zensearch.R
import com.five.zensearch.com.five.zensearch.data.dto.UserDTO
import com.five.zensearch.com.five.zensearch.presentation.logInFragment.LogInViewModel
import com.five.zensearch.databinding.FragmentLogInBinding

class LogInFragment : Fragment() {

    private lateinit var binding: FragmentLogInBinding

    private lateinit var viewModel: LogInViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentLogInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[LogInViewModel::class.java]
        observeViewModel()
        setUpClicks()
    }

    private fun setUpClicks() {
        binding.loginConfirmButton.setOnClickListener {
            val name = binding.loginNameEdittext.text.toString()
            val password = binding.loginPasswordEdittext.text.toString()
            viewModel.logIn(name, password)

        }
        binding.singUpNavigate.setOnClickListener {
            findNavController().navigate(R.id.action_logInFragment_to_registrationFragment)
        }
    }

    private fun observeViewModel() {
        viewModel.error.observe(viewLifecycleOwner) {
            if (it != null) {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }
        viewModel.user.observe(viewLifecycleOwner) {
            if (it != null) {
                viewModel.refreshToken()
                findNavController().navigate(R.id.action_logInFragment_to_homeFragment)
            }
        }
    }
}