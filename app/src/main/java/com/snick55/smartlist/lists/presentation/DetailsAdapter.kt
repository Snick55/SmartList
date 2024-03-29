package com.snick55.smartlist.lists.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.snick55.smartlist.R
import com.snick55.smartlist.databinding.ItemListDetailsBinding

class DetailsAdapter(
   private val onLongPressed: (id: String)->Unit
) :
    ListAdapter<ListItemDetails, DetailsAdapter.DetailsViewHolder>(DetailUiDiffCallback()),View.OnLongClickListener {


    inner class DetailsViewHolder(private val binding: ItemListDetailsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(itemUi: ListItemDetails) {
            binding.root.tag = itemUi

                binding.nameTV.text = itemUi.name
                binding.countTV.text = itemUi.count
                binding.dateTextView.text = binding.root.context.getString(
                    R.string.details_item_date,
                    itemUi.dateFrom,
                    itemUi.dateTo
                )

        }
    }


    override fun onLongClick(v: View): Boolean {
        val itemUi = v.tag as ListItemDetails
        onLongPressed.invoke(itemUi.id)
        return true
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemListDetailsBinding.inflate(inflater, parent, false)
        binding.root.setOnLongClickListener(this)
        return DetailsViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: DetailsViewHolder, position: Int) {
        viewHolder.bind(getItem(position))
    }

    class DetailUiDiffCallback : DiffUtil.ItemCallback<ListItemDetails>() {

        override fun areItemsTheSame(oldItem: ListItemDetails, newItem: ListItemDetails): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ListItemDetails,
            newItem: ListItemDetails
        ): Boolean {
            return oldItem == newItem
        }
    }
}