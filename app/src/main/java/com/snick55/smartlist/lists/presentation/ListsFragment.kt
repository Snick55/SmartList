package com.snick55.smartlist.lists.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.snick55.smartlist.R
import com.snick55.smartlist.core.viewBinding
import com.snick55.smartlist.databinding.FragmentListsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListsFragment: Fragment(R.layout.fragment_lists) {

    private val binding by viewBinding<FragmentListsBinding>()
    private val viewModel by viewModels<ListsViewModel>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ListsAdapter()
        viewModel.setData(adapter)
        binding.listsRecyclerView.adapter = adapter


    }


}