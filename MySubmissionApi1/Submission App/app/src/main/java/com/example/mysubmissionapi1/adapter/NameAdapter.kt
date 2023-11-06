package com.example.mysubmissionapi1.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mysubmissionapi1.data.ItemsItem
import com.example.mysubmissionapi1.databinding.ItemRowNameBinding
import com.example.mysubmissionapi1.ui.UserDetail

class NameAdapter : ListAdapter<ItemsItem, NameAdapter.ListViewHolder>(DIFF_CALLBACK) {

    class ListViewHolder(val binding : ItemRowNameBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind (name: ItemsItem) {
            binding.tvItemName.text = "${name.login}\n"
            Glide.with(binding.root.context)
                .load(name.avatarUrl)
                .circleCrop()
                .into(binding.imgItemPhoto)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemRowNameBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val name = getItem(position)
        holder.bind(name)

        holder.itemView.setOnClickListener {
            val intentDetail = Intent(holder.itemView.context, UserDetail::class.java)
            intentDetail.putExtra(UserDetail.ID, name.id)
            intentDetail.putExtra(UserDetail.LOGIN, name.login)
            intentDetail.putExtra(UserDetail.URL, name.avatarUrl)

            holder.itemView.context.startActivity(intentDetail)  }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ItemsItem>() {
            override fun areItemsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}