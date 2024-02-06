package com.snick55.smartlist.createProducts.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.snick55.smartlist.R
import com.snick55.smartlist.core.viewBinding
import com.snick55.smartlist.databinding.FragmentCreateProductBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentCreateProduct: Fragment(R.layout.fragment_create_product) {

    private val binding by viewBinding<FragmentCreateProductBinding>()
    private val viewModel by viewModels<CreateProductViewModel>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.goBackButton.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.confirmButton.setOnClickListener {
            if (binding.nameEditText.text.isNullOrBlank()){
                binding.nameInputLayout.isErrorEnabled = true
                binding.nameInputLayout.error = getString(R.string.field_is_empty)
                return@setOnClickListener
            }
            val name = binding.nameEditText.text.toString().trim()
            val count = binding.countEditText.text?.toString()
            val dateFrom = binding.dateFromEditText.text?.toString()
            val dateTo = binding.dateToEditText.text?.toString()

            viewModel.createProduct(name,count,dateFrom,dateTo)
            findNavController().popBackStack()
        }

    }

}