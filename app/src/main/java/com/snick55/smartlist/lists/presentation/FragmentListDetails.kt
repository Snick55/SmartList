package com.snick55.smartlist.lists.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.snick55.smartlist.R
import com.snick55.smartlist.core.viewBinding
import com.snick55.smartlist.databinding.FragmentListDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FragmentListDetails: Fragment(R.layout.fragment_list_details) {

    private val binding by viewBinding<FragmentListDetailsBinding>()
    private val viewModel by viewModels<ListDetailsViewModel>()
    private val args: FragmentListDetailsArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getDetails(args.listId)
        binding.listNameTV.text = args.listName
        binding.goBackButton.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.addProductButton.setOnClickListener {
            val action = FragmentListDetailsDirections.actionFragmentListDetailsToFragmentCreateProduct()
            findNavController().navigate(action)
        }
        val adapter = DetailsAdapter()

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.items.collect {
                    adapter.submitList(it)
                }
            }
        }

        binding.itemsRecyclerView.adapter = adapter
    }

}