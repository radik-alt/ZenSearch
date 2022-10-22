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
import com.five.zensearch.databinding.FragmentCreateEventBinding
import com.five.zensearch.databinding.FragmentLogInBinding

class LogInFragment : Fragment() {

    private lateinit var binding: FragmentLogInBinding

    private val viewModel: LogInViewModel by lazy {
        ViewModelProvider(requireActivity())[LogInViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLogInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.loginNameEdittext.setText("v@emiryan.ru")
        binding.loginPasswordEdittext.setText("qwerty")
//        observeViewModel()
        setUpClicks()
    }

    private fun setUpClicks() {
        binding.loginConfirmButton.setOnClickListener {
            val name = binding.loginNameEdittext.toString()
            val password = binding.loginPasswordEdittext.toString()
            if (isLoginFormValid(name, password)){
                viewModel.refreshToken()
                viewModel.logIn(name, password)
            }
            else
                sendError()
        }
        binding.loginRegistrationButton.setOnClickListener {
            findNavController().navigate(R.id.action_logInFragment_to_registrationFragment)
        }
    }

    private fun observeViewModel() {
        viewModel.error.observe(viewLifecycleOwner) {
            if (it != null) {
                sendError()
            }
        }
        viewModel.user.observe(viewLifecycleOwner) {
            if (it != null) {
                viewModel.refreshToken()
                successLogIn()
            }
        }

    }

    private fun isLoginFormValid(name: String, password: String) =
        name.isNotEmpty() && password.isNotEmpty()

    private fun sendError() {
        Toast.makeText(context, ERROR_MESSAGE, Toast.LENGTH_SHORT).show()
    }

    private fun successLogIn() {
        findNavController().navigate(R.id.action_logInFragment_to_homeFragment)
    }

    companion object {
        const val ERROR_MESSAGE = "Неверный логин или пароль"
    }

}