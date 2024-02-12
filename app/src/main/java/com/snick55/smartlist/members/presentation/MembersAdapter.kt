package com.snick55.smartlist.members.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.snick55.smartlist.databinding.ItemMembersBinding
import com.snick55.smartlist.lists.presentation.ListItemUi

class MembersAdapter(private val onItemClicked: (MemberUi) -> Unit) :
    ListAdapter<MemberUi, MembersAdapter.MembersViewHolder>(DetailUiDiffCallback()), View.OnClickListener {


    inner class MembersViewHolder(private val binding: ItemMembersBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(itemUi: MemberUi) {
            binding.root.tag = itemUi
            binding.nameTV.text = itemUi.name
            binding.numberTextView.text = itemUi.phoneNumber
        }
    }

    override fun onClick(view: View) {
        val itemUi = view.tag as MemberUi
        onItemClicked(itemUi)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MembersViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemMembersBinding.inflate(inflater, parent, false)
        binding.root.setOnClickListener(this)
        return MembersViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: MembersViewHolder, position: Int) {
        viewHolder.bind(getItem(position))
    }


    class DetailUiDiffCallback : DiffUtil.ItemCallback<MemberUi>() {

        override fun areItemsTheSame(oldItem: MemberUi, newItem: MemberUi): Boolean {
            return oldItem.phoneNumber == newItem.phoneNumber
        }

        override fun areContentsTheSame(
            oldItem: MemberUi,
            newItem: MemberUi
        ): Boolean {
            return oldItem == newItem
        }
    }
}