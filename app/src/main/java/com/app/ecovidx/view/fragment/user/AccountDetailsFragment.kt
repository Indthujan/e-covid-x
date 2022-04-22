package com.app.ecovidx.view.fragment.user

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.app.ecovidx.R
import com.app.ecovidx.databinding.FragmentAccountDetailsBinding

class AccountDetailsFragment: Fragment(R.layout.fragment_account_details) {

    private lateinit var binding: FragmentAccountDetailsBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val accountDetailsBinding = FragmentAccountDetailsBinding.bind(view)
        binding = accountDetailsBinding

    }
}