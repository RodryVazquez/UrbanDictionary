package com.example.urbandictionary.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.urbandictionary.databinding.ItemCardBinding

class ItemListAdapter() : ListAdapter<ItemCard, TaskCardHolder>(ItemCardDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskCardHolder {
        val itemBinding =
            ItemCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskCardHolder(itemBinding)
    }

    override fun getItemCount(): Int = currentList.size

    override fun onBindViewHolder(holder: TaskCardHolder, position: Int) {
        val taskCard: ItemCard = currentList[position]
        holder.bind(taskCard)
    }
}

class TaskCardHolder(
    private val binding: ItemCardBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(itemCard: ItemCard) {

        binding.apply {
            title.text = itemCard.word
            subtitle.text = itemCard.definition
            secondSubTitle.text = itemCard.permalink
            footerTitle.text = itemCard.author
            footerDate.text = itemCard.written_on

            thumbUpCount.text = itemCard.thumbsUp.toString()
            thumbDownCount.text = itemCard.thumbsDown.toString()
        }
    }
}

class ItemCardDiffCallback : DiffUtil.ItemCallback<ItemCard>() {
    override fun areItemsTheSame(oldItem: ItemCard, newItem: ItemCard): Boolean {
        return oldItem.defId == newItem.defId
    }

    override fun areContentsTheSame(oldItem: ItemCard, newItem: ItemCard): Boolean {
        return oldItem == newItem
    }
}

data class ItemCard(
    val defId: Int,
    val word: String,
    val definition: String,
    val permalink: String,
    val author: String,
    val example: String,
    val written_on: String,
    val thumbsUp: Int,
    val thumbsDown: Int

)