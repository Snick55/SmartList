package com.snick55.smartlist.login.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.redmadrobot.inputmask.MaskedTextChangedListener
import com.redmadrobot.inputmask.PhoneInputListener
import com.snick55.smartlist.R
import com.snick55.smartlist.core.Container
import com.snick55.smartlist.core.observe
import com.snick55.smartlist.core.viewBinding
import com.snick55.smartlist.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment: Fragment(R.layout.fragment_login) {

    private val binding by viewBinding<FragmentLoginBinding>()
    private val viewModel by viewModels<LoginViewModel>()
    private var listener: PhoneInputListener? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupPhone()
        binding.root.setTryAgainListener {
            binding.root.container = Container.Success("")
        }
        binding.enterPhoneNumber.setOnClickListener {
            val phoneNumber = binding.phoneEditText.text.toString()
            viewModel.getCode(phoneNumber,requireActivity())
        }

        binding.root.observe(viewLifecycleOwner,viewModel.state){
            if (it.isNotEmpty()) {
                val directions = LoginFragmentDirections.actionLoginFragmentToCodeFragment(it)
                findNavController().navigate(directions)
            }
        }
    }



    private fun setupPhone() {
        val editText = binding.phoneEditText
        val countriesTextView = binding.countriesTextView
        listener = PhoneInputListener.installOn(
            editText,
            autocomplete = true,
            autoskip = false,
            listener = null,
            valueListener = object : MaskedTextChangedListener.ValueListener {
                override fun onTextChanged(
                    maskFilled: Boolean,
                    extractedValue: String,
                    formattedValue: String,
                    tailPlaceholder: String
                ) {
                    val countriesList = StringBuilder()
                    binding.enterPhoneNumber.isEnabled = maskFilled
                    this@LoginFragment.listener?.computedCountries?.forEach { country ->
                        countriesList
                            .append(country.countryCode)
                            .append(" ")
                            .append(country.emoji)
                            .append(" ")
                            .append(country.name)
                            .append("\n")
                    }
                    countriesTextView.text = countriesList.toString()
                }
            }
        )
        editText.hint = listener?.placeholder()
    }

}