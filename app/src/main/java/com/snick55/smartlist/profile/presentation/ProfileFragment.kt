package com.snick55.smartlist.profile.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.snick55.smartlist.R
import com.snick55.smartlist.core.Container
import com.snick55.smartlist.core.viewBinding
import com.snick55.smartlist.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ProfileFragment: Fragment(R.layout.fragment_profile) {

    private val binding by viewBinding<FragmentProfileBinding>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.root.container = Container.Success("")

    }
}