package com.example.mysubmissionapi1.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mysubmissionapi1.adapter.NameAdapter
import com.example.mysubmissionapi1.data.ItemsItem
import com.example.mysubmissionapi1.databinding.ActivityFavoriteBinding
import com.example.mysubmissionapi1.model.FavoriteViewModel
import com.example.mysubmissionapi1.model.MainViewModel

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.githubName.layoutManager = layoutManager

        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.githubName.addItemDecoration(itemDecoration)

        val favoriteViewModel = ViewModelProvider(this)[FavoriteViewModel::class.java]
        val adapter = NameAdapter()

        favoriteViewModel.getFavoritedUser()?.observe(this) { users ->
            val items = arrayListOf<ItemsItem>()
            users.map {
                val item = ItemsItem(id = it.id, login = it.username, avatarUrl = it.avatarUrl)
                items.add(item)
            }
            adapter.submitList(items)
            binding.githubName.adapter = adapter
        }
    }
}