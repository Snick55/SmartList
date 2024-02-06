package com.snick55.smartlist.members.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.snick55.smartlist.R
import com.snick55.smartlist.core.viewBinding
import com.snick55.smartlist.databinding.FragmentMembersBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MembersFragment: Fragment(R.layout.fragment_members) {

    private val binding by viewBinding<FragmentMembersBinding>()
    private val viewModel by viewModels<MembersViewModel>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadMembers()

        binding.goBackButton.setOnClickListener {
            findNavController().popBackStack()
        }

        val adapter = MembersAdapter()
        binding.membersRecyclerView.adapter = adapter
        viewModel.members.observe(viewLifecycleOwner){

            adapter.submitList(it)
        }
    }


}