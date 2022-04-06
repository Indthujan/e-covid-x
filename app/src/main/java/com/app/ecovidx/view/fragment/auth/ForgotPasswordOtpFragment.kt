package com.app.ecovidx.view.fragment.auth

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.app.ecovidx.R
import com.app.ecovidx.databinding.FragmentPwdResetOtpBinding
import com.app.ecovidx.model.Password
import com.app.ecovidx.utils.Resource
import com.app.ecovidx.view.activity.MainActivity
import com.app.ecovidx.viewmodel.AuthViewModel

class ForgotPasswordOtpFragment : Fragment(R.layout.fragment_pwd_reset_otp) {

    private lateinit var binding: FragmentPwdResetOtpBinding
    private lateinit var viewModel: AuthViewModel
    private val args: ForgotPasswordOtpFragmentArgs by navArgs()
    var otp: String = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragmentPwdResetOtpBinding = FragmentPwdResetOtpBinding.bind(view)
        binding = fragmentPwdResetOtpBinding

        viewModel = (activity as MainActivity).viewModel

        viewModel.verifyOtp.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    it.data?.let { password ->
                        if (password.message == "Invalied OTP")
                            Toast.makeText(context, "Check your Input", Toast.LENGTH_SHORT)
                                .show()
                        else
                            findNavController().navigate(
                                ForgotPasswordOtpFragmentDirections.actionForgotPasswordOtpFragmentToForgotPasswordResetFragment(
                                    password.hash
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

        println("sdsadd " + args.otp)

        fragmentPwdResetOtpBinding.otpView.setOtpCompletionListener {
            otp = it
        }
        fragmentPwdResetOtpBinding.verifyP.setOnClickListener {

            if (otp == args.otp)
                viewModel.verifyOTP(Password("", "", "", "", "", otp, args.userId))
            else
                Toast.makeText(context, "Check OTP", Toast.LENGTH_SHORT).show()
        }
        fragmentPwdResetOtpBinding.toolbar.backView.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}