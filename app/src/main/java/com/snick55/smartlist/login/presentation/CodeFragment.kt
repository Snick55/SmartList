package com.snick55.smartlist.login.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.snick55.smartlist.R
import com.snick55.smartlist.core.viewBinding
import com.snick55.smartlist.databinding.FragmentCodeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CodeFragment: Fragment(R.layout.fragment_code) {

    private val binding by viewBinding<FragmentCodeBinding>()
    private val viewModel by viewModels<CodeViewModel>()
    private val args by navArgs<CodeFragmentArgs>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.enterCode.setOnClickListener {
            viewModel.signInWithCode(binding.codeEditText.text.toString(),args.verificationId)
        }


        viewModel.state.observe(viewLifecycleOwner){
          TODO()
        }

    }

}