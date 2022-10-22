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


    private var _binding: FragmentLogInBinding?=null
    private val binding:FragmentLogInBinding
        get() = _binding ?: throw RuntimeException("FragmentLogInBinding == null")


    private val viewModel: LogInViewModel by lazy {
        ViewModelProvider(requireActivity())[LogInViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLogInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loginConfirmButton.setOnClickListener {
            authUser()
        }

        binding.singUpNavigate.setOnClickListener {
            findNavController().navigate(R.id.action_logInFragment_to_registrationFragment)
        }
    }

    private fun authUser() {
        val name = binding.loginNameEdittext.text.toString()
        val password = binding.loginPasswordEdittext.text.toString()
        if (isLoginFormValid(name, password)) {
            viewModel.onLogInButtonPressed(name, password)
            findNavController().navigate(R.id.action_logInFragment_to_homeFragment)
        }
        else
            sendError()
    }


    private fun isLoginFormValid(name: String, password: String) = name.isNotEmpty() && password.isNotEmpty()

    private fun sendError() {
        Toast.makeText(context, ERROR_MESSAGE, Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val ERROR_MESSAGE = "Неверный логин или пароль"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}