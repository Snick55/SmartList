package com.snick55.smartlist.lists.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.snick55.smartlist.R
import com.snick55.smartlist.core.viewBinding
import com.snick55.smartlist.databinding.FragmentAddListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddListFragment : Fragment(R.layout.fragment_add_list) {

    private val binding by viewBinding<FragmentAddListBinding>()
    private val viewModel by viewModels<AddListViewModel>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.createListButton.setOnClickListener {
            viewModel.createList(binding.nameET.text.toString())
        }
    }

}