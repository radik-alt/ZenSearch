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

class LogInFragment : Fragment() {

    lateinit var loginName: EditText
    lateinit var loginPassword: EditText
    lateinit var confirmButton: Button
    lateinit var registrationButton: Button



    private val viewModel: LogInViewModel by lazy {
        ViewModelProvider(requireActivity())[LogInViewModel::class.java]
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_log_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginName = view.findViewById(R.id.login_name_edittext)
        loginPassword = view.findViewById(R.id.login_password_edittext)
        confirmButton = view.findViewById(R.id.login_confirm_button)
        registrationButton = view.findViewById(R.id.login_registration_button)
        setUpClicks()
    }

    private fun setUpClicks() {
        confirmButton.setOnClickListener {
            val name = loginName.text.toString()
            val password = loginPassword.text.toString()
            if (isLoginFormValid(name, password))
                viewModel.onLogInButtonPressed(name, password)
            else
                sendError()
        }
        registrationButton.setOnClickListener {
            findNavController().navigate(R.id.action_logInFragment_to_registrationFragment)
        }
    }

    private fun isLoginFormValid(name: String, password: String) = name.isNotEmpty() && password.isNotEmpty()

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