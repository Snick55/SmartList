package com.snick55.smartlist.splash

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.snick55.smartlist.MainActivity
import com.snick55.smartlist.R
import com.snick55.smartlist.core.viewBinding
import com.snick55.smartlist.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SplashFragment: Fragment(R.layout.fragment_splash) {


    private val viewModel by viewModels<SplashViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.launchState.observe(viewLifecycleOwner){
            val intent = Intent(requireContext(),MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or  Intent.FLAG_ACTIVITY_CLEAR_TASK)
            intent.putExtra("isAuthorize",it)
            startActivity(intent)
        }

    }


}