package com.snick55.smartlist.lists.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.snick55.smartlist.databinding.ListItemBinding

class ListsAdapter(
   private val onListClicked: (String,String) -> Unit
): ListAdapter<ListItemUi, ListsAdapter.MyViewHolder>(ListUiDiffCallback()), View.OnClickListener {

    inner class MyViewHolder(private val binding: ListItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(itemUi: ListItemUi){
            binding.root.tag = itemUi
            binding.nameTV.text = itemUi.name
            binding.dateTextView.text = itemUi.date
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemBinding.inflate(inflater,parent,false)
        binding.root.setOnClickListener(this)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onClick(view: View) {
       val itemUi = view.tag as ListItemUi
        onListClicked.invoke(itemUi.id,itemUi.name)
    }

    class ListUiDiffCallback : DiffUtil.ItemCallback<ListItemUi>() {
        override fun areItemsTheSame(oldItem: ListItemUi, newItem: ListItemUi): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ListItemUi, newItem: ListItemUi): Boolean {
            return oldItem == newItem
        }
    }
}