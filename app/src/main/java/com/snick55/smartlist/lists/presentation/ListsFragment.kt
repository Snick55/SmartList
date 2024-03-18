package com.snick55.smartlist.lists.presentation

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.snick55.smartlist.R
import com.snick55.smartlist.core.observe
import com.snick55.smartlist.core.viewBinding
import com.snick55.smartlist.databinding.FragmentListsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListsFragment : Fragment(R.layout.fragment_lists) {

    private val binding by viewBinding<FragmentListsBinding>()
    private val viewModel by viewModels<ListsViewModel>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ListsAdapter(onListClicked = { id, name ->
            val action = ListsFragmentDirections.actionListsFragmentToFragmentListDetails(id, name)
            findNavController().navigate(action)
        }, onLongItemClicked = { id ->
            val dialogBuilder = AlertDialog.Builder(requireContext())
            DeleteListDialog.confirm(dialogBuilder,getString(R.string.confirm_delete_list)) {
                viewModel.leaveAndDeleteList(id)
            }
        })
        binding.listsRecyclerView.adapter = adapter

        binding.root.observe(viewLifecycleOwner, viewModel.lists) {
            adapter.submitList(it)
            binding.listsRecyclerView.scrollToPosition(0)
        }


        binding.addNewList.setOnClickListener {
            findNavController().navigate(R.id.action_listsFragment_to_addListFragment)
        }

        binding.root.setTryAgainListener {
            viewModel.tryAgain()
        }
    }

}