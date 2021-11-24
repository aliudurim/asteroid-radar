package com.udacity.asteroidradar.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.databinding.MainListItemBinding
import com.udacity.asteroidradar.models.Asteroid

class MainAdapter(private val itemListener: MainItemListener) :
    ListAdapter<Asteroid, MainAdapter.MainViewHolder>(MainDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, itemListener)
    }

    class MainViewHolder private constructor(private val binding: MainListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Asteroid, itemListener: MainItemListener) {
            binding.data = data
            binding.itemClick = itemListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): MainViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = MainListItemBinding.inflate(
                    layoutInflater, parent, false
                )
                return MainViewHolder(binding)
            }
        }
    }
}

internal class MainDiffCallback : DiffUtil.ItemCallback<Asteroid>() {
    override fun areItemsTheSame(
        oldItem: Asteroid,
        newItem: Asteroid
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: Asteroid,
        newItem: Asteroid
    ): Boolean {
        return oldItem == newItem
    }
}

class MainItemListener(val clickListener: (item: Asteroid) -> Unit) {
    fun onClick(item: Asteroid) = clickListener(item)
}