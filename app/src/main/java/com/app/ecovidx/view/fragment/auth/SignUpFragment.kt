package com.app.ecovidx.view.fragment.auth

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.app.ecovidx.R
import com.app.ecovidx.databinding.FragmentSignUpBinding
import com.app.ecovidx.data.model.Register
import com.app.ecovidx.utils.Resource
import com.app.ecovidx.view.activity.MainActivity
import com.app.ecovidx.viewmodel.AuthViewModel

class SignUpFragment : Fragment(R.layout.fragment_sign_up) {

    private lateinit var viewModel: AuthViewModel
    private lateinit var binding: FragmentSignUpBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragmentSignUpBinding = FragmentSignUpBinding.bind(view)
        binding = fragmentSignUpBinding

        viewModel = (activity as MainActivity).viewModel

        viewModel.registerResponse.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    response.data?.let {
                        if (it.message == "User successfully registered") {
                            findNavController().popBackStack()
                            context?.let { context ->
                                Toast.makeText(
                                    context,
                                    "Sign In to continue",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }

                        else {
                            context?.let { context ->
                                Toast.makeText(
                                    context,
                                    "User already exists",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }
                is Resource.Error -> {
                    response.message?.let { message ->

                    }
                }
//                is Resource.Loading -> TODO()
            }
        })

        fragmentSignUpBinding.signUp.setOnClickListener {
            signUpUser()
        }
        fragmentSignUpBinding.haveAccSignIn.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun signUpUser() {
        val name = binding.name.text
        val mail = binding.mailAddress.text
        val password = binding.password.text
        val confirmPassword = binding.confirmPassword.text
        val result = password.toString() == confirmPassword.toString()

        if (mail.isNullOrEmpty() || name.isNullOrEmpty())
            context?.let { context ->
                Toast.makeText(context, "Enter mail/password to Sign In", Toast.LENGTH_SHORT).show()
            }
        else if (password!!.length < 8)
            context?.let { context ->
                Toast.makeText(
                    context,
                    "Enter password with more than 8 characters",
                    Toast.LENGTH_SHORT
                ).show()
            }
        else if (!result)
            context?.let { context ->
                Toast.makeText(context, "Password mismatch", Toast.LENGTH_SHORT).show()
            }
        else
            viewModel.userRegistration(
                Register(
                    confirmPassword.toString(),
                    mail.toString(),
                    name.toString(),
                    password.toString()
                )
            )
    }
}