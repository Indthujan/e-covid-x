package com.app.ecovidx.view.fragment.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.app.ecovidx.R
import com.app.ecovidx.databinding.FragmentProfileBinding
import com.app.ecovidx.view.activity.HomeActivity
import com.app.ecovidx.view.activity.MainActivity

class ProfileFragment: Fragment(R.layout.fragment_profile) {

    private lateinit var binding: FragmentProfileBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragmentProfileBinding = FragmentProfileBinding.bind(view)
        binding = fragmentProfileBinding

        binding.btn.setOnClickListener {
            val sharedPref = requireActivity().getSharedPreferences("access_token", Context.MODE_PRIVATE)
                ?: return@setOnClickListener
            sharedPref.edit().clear().apply()

            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
    }
}