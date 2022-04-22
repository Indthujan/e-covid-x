package com.app.ecovidx.view.fragment.auth

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.app.ecovidx.R
import com.app.ecovidx.databinding.FragmentPwdResetNewBinding
import com.app.ecovidx.data.model.Password
import com.app.ecovidx.utils.Resource
import com.app.ecovidx.view.activity.MainActivity
import com.app.ecovidx.viewmodel.AuthViewModel

class ForgotPasswordResetFragment : Fragment(R.layout.fragment_pwd_reset_new) {

    private lateinit var binding: FragmentPwdResetNewBinding
    private lateinit var viewModel: AuthViewModel
    private val args: ForgotPasswordResetFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragmentPwdResetNewBinding = FragmentPwdResetNewBinding.bind(view)
        binding = fragmentPwdResetNewBinding

        viewModel = (activity as MainActivity).viewModel
        viewModel.passwordReset.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    it.data?.let { password ->
                        if (password.message == "Invalied Hash")
                            Toast.makeText(context, "Check your Input", Toast.LENGTH_SHORT)
                                .show()
                        else
                            findNavController().navigate(R.id.action_forgotPasswordResetFragment_to_signInFragment)

                    }
                }
                is Resource.Error -> {
                    it.message?.let { message ->

                    }
                }
//                is Resource.Loading -> TODO()
            }
        })
        fragmentPwdResetNewBinding.resetPassword.setOnClickListener {
            resetNewPassword()
        }
        fragmentPwdResetNewBinding.fprToolbar.backView.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun resetNewPassword() {

        val password = binding.newPassword.text
        val confirmPassword = binding.confirmPassword.text

        if (!password.isNullOrEmpty() || !confirmPassword.isNullOrEmpty())
            if (password.toString() == confirmPassword.toString())
                viewModel.passwordReset(
                    Password(
                        "",
                        confirmPassword.toString(),
                        args.hash.toString(),
                        "",
                        password.toString(),
                        "",
                        0
                    )
                )
            else
                Toast.makeText(context, "Check your Input", Toast.LENGTH_SHORT).show()
    }
}