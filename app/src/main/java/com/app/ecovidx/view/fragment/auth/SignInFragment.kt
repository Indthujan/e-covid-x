package com.app.ecovidx.view.fragment.auth

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.app.ecovidx.R
import com.app.ecovidx.databinding.FragmentSignInBinding
import com.app.ecovidx.utils.Resource
import com.app.ecovidx.view.activity.MainActivity
import com.app.ecovidx.viewmodel.AuthViewModel
import com.app.ecovidx.data.model.Login
import com.app.ecovidx.view.activity.HomeActivity

class SignInFragment : Fragment(R.layout.fragment_sign_in) {

    lateinit var viewModel: AuthViewModel
    private var binding: FragmentSignInBinding? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragmentSignInBinding = FragmentSignInBinding.bind(view)
        binding = fragmentSignInBinding

        viewModel = (activity as MainActivity).viewModel

        viewModel.loginResponse.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    response.data?.let {
                        if (it.error == "Unauthorized") {
                            Toast.makeText(context, "Credentials mismatch", Toast.LENGTH_SHORT)
                                .show()
                        } else {
                            val sharedPreferences: SharedPreferences =
                                (activity as MainActivity).getSharedPreferences(
                                    "access_token",
                                    Context.MODE_PRIVATE
                                )
                            val editor: SharedPreferences.Editor = sharedPreferences.edit()
                            editor.putString("access_token", it.access_token)
                            editor.apply()

                            val intent = Intent(activity, HomeActivity::class.java)
                            startActivity(intent)
                            (activity as MainActivity).finish()
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

        fragmentSignInBinding.signIn.setOnClickListener {
            signInUser()
        }
        fragmentSignInBinding.noMemberSignUp.setOnClickListener {
            findNavController().navigate(SignInFragmentDirections.actionSignInFragmentToSignUpFragment())
        }
        fragmentSignInBinding.forgetPassword.setOnClickListener {
            findNavController().navigate(SignInFragmentDirections.actionSignInFragmentToForgotPasswordMailFragment())
        }
    }

    private fun signInUser() {

        val mail = binding?.mailAddress?.text
        val password = binding?.password?.text

        if (mail.isNullOrEmpty() || password.isNullOrEmpty())
            context?.let { context ->
                Toast.makeText(context, "Enter mail/password to Sign In", Toast.LENGTH_SHORT).show()
            }
        else
            viewModel.postLoginCredentials(Login(mail.toString(), password.toString()))
    }
}