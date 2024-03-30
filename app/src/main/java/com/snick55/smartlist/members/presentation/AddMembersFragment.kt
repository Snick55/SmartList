package com.snick55.smartlist.members.presentation

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.snick55.smartlist.R
import com.snick55.smartlist.core.log
import com.snick55.smartlist.core.viewBinding
import com.snick55.smartlist.databinding.FragmentAddMembersBinding
import com.snick55.smartlist.members.presentation.AddMembersViewModel.State.Failure
import com.snick55.smartlist.members.presentation.AddMembersViewModel.State.Loading
import com.snick55.smartlist.members.presentation.AddMembersViewModel.State.Success
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddMembersFragment : Fragment(R.layout.fragment_add_members) {

    private val binding by viewBinding<FragmentAddMembersBinding>()
    private val viewModel by viewModels<AddMembersViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.goBack.observe(viewLifecycleOwner){
            if (it) findNavController().popBackStack()
        }

        binding.findMemberSearchView.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrBlank()) {
                    viewModel.getUsersByPhone(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })

        val adapter = MembersAdapter(onItemClicked = {
            val dialogBuilder = AlertDialog.Builder(requireContext())
            AddMemberDialog.confirm(dialogBuilder){
                viewModel.addMember(it)
            }
        })
        binding.addMembersRecyclerView.adapter = adapter


        viewModel.state.observe(viewLifecycleOwner){
            binding.progress.isVisible = it is Loading
            binding.addMembersRecyclerView.isVisible = it is Success
            binding.errorTextView.isVisible = it is Failure
            if (it is Success){
                adapter.submitList(it.users)
            }else if(it is Failure){
                binding.errorTextView.text = it.error
            }
        }

    }
}