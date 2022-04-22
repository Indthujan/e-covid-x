package com.app.ecovidx.view.fragment.auth

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.app.ecovidx.R
import com.app.ecovidx.databinding.FragmentPwdResetMailBinding
import com.app.ecovidx.data.model.Password
import com.app.ecovidx.utils.Resource
import com.app.ecovidx.view.activity.MainActivity
import com.app.ecovidx.viewmodel.AuthViewModel

class ForgotPasswordMailFragment : Fragment(R.layout.fragment_pwd_reset_mail) {

    private lateinit var binding: FragmentPwdResetMailBinding
    private lateinit var viewModel: AuthViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragmentPwdResetMailBinding = FragmentPwdResetMailBinding.bind(view)
        binding = fragmentPwdResetMailBinding

        viewModel = (activity as MainActivity).viewModel
        viewModel.verifyMail.value = null

        viewModel.verifyMail.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    it.data?.let { password ->
                        if (password.message == "Email Not Exist")
                            Toast.makeText(context, "Check your Input", Toast.LENGTH_SHORT)
                                .show()
                        else
                            findNavController().navigate(
                                ForgotPasswordMailFragmentDirections.actionForgotPasswordMailFragmentToForgotPasswordOtpFragment(
                                    password.otp, password.user_id
                                )
                            )
                    }
                }
                is Resource.Error -> {
                    it.message?.let { message ->

                    }
                }
//                is Resource.Loading -> TODO()
            }
        })
        fragmentPwdResetMailBinding.verify.setOnClickListener {

            val mail = fragmentPwdResetMailBinding.mailAddress.text

            if (mail.isNullOrEmpty()) {
                Toast.makeText(context, "Credentials mismatch", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }

            viewModel.verifyMail(Password(mail.toString(), "", "", "", "", "", 0))
        }

        fragmentPwdResetMailBinding.fpmToolbar.backView.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}