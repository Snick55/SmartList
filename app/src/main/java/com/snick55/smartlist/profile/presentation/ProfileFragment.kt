package com.snick55.smartlist.profile.presentation

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.navOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.snick55.smartlist.R
import com.snick55.smartlist.core.*
import com.snick55.smartlist.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private val binding by viewBinding<FragmentProfileBinding>()
    private val viewModel by viewModels<ProfileViewModel>()
    private var curName = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.resultView.observe(viewLifecycleOwner, viewModel.account) {
            curName = it.name
            binding.nameTV.text = it.name
            binding.numberTV.text = it.phone
        }


        binding.testBtn.setOnClickListener {
            val name = binding.nameET.text?.trim().toString()
            if (curName == name || name.isEmpty()) return@setOnClickListener
            viewModel.changeName(name)
            binding.nameET.text?.clear()
            binding.nameET.clearFocus()
        }


        binding.logout.setOnClickListener {
            viewModel.logout()
            findTopNavController().navigate(
                R.id.action_tabsFragment_to_loginFragment,
                null,
                navOptions {
                    popUpTo(R.id.tabsFragment) {
                        inclusive = true
                    }
                })

        }

    }


}