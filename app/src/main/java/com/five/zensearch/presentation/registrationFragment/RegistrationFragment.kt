package com.five.zensearch.presentation.registrationFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.five.zensearch.App
import com.five.zensearch.R
import com.five.zensearch.com.five.zensearch.data.dto.UserDTO
//import com.five.zensearch.com.five.zensearch.presentation.registrationFragment.SingUpViewModel
import com.five.zensearch.databinding.FragmentRegistrationBinding

class RegistrationFragment : Fragment() {

    private var _binding: FragmentRegistrationBinding?=null
    private val binding:FragmentRegistrationBinding
        get() = _binding ?: throw RuntimeException("FragmentRegistrationBinding == null")

//    private val singUpViewModel: SingUpViewModel by lazy {
//        ViewModelProvider(this)[SingUpViewModel::class.java]
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        binding.singUpUser.setOnClickListener {
//            val firstName = binding.firstName.text.toString().trim()
//            val lastName = binding.lastName.text.toString().trim()
//            val email = binding.email.text.toString().trim()
//            val password = binding.password.text.toString().trim()
//            if (validSingUp(firstName, lastName, email, password)){
////                val user = UserDTO(it.user?.uid, "Vova", "Emiryan", 19, "Hach", false, App.getToken(), "img")
////                singUpViewModel.singUp(email, password, user)
//                findNavController()
//            }
//        }

    }



//    private fun validSingUp(firstName:String, lastName:String, password:String, reportPassword:String):Boolean{
//        if (firstName.isNotEmpty() && lastName.isNotEmpty() && password.isNotEmpty() && reportPassword.isNotEmpty()){
//            singUpViewModel.showToast(requireContext(), "Ввеедите все поля...")
//            return true
//        } else if (password == reportPassword) {
//            singUpViewModel.showToast(requireContext(), "Проверьте пароли...")
//            return true
//        } else {
//            return false
//        }
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}