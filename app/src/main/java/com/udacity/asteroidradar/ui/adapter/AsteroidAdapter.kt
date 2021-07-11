package com.udacity.asteroidradar.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.data.Asteroid
import com.udacity.asteroidradar.databinding.AsteroidItemListBinding

class AsteroidAdapter(val callback: AsteroidClick) :
    ListAdapter<Asteroid, AsteroidViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsteroidViewHolder =
        AsteroidViewHolder.from(parent)


    override fun onBindViewHolder(holder: AsteroidViewHolder, position: Int) {
        val asteroid = getItem(position)
        holder.bind(asteroid, callback)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Asteroid>() {
        override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
            return oldItem.id == newItem.id
        }
    }
}


class AsteroidViewHolder(val viewDataBinding: AsteroidItemListBinding) :
    RecyclerView.ViewHolder(viewDataBinding.root) {
    fun bind(asteroid: Asteroid, clickListener: AsteroidClick) {
        viewDataBinding.asteroid = asteroid
        viewDataBinding.asteroidClick = clickListener

        viewDataBinding.executePendingBindings()
    }

    companion object {
        @LayoutRes
        val LAYOUT = R.layout.asteroid_item_list

        fun from(parent: ViewGroup): AsteroidViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = AsteroidItemListBinding.inflate(inflater, parent, false)
            return AsteroidViewHolder(binding)
        }
    }
}

class AsteroidClick(val block: (asteroid: Asteroid) -> Unit) {
    fun onClick(asteroid: Asteroid) = block(asteroid)
}