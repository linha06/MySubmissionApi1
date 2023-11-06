package com.example.mysubmissionapi1.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.mysubmissionapi1.R
import com.example.mysubmissionapi1.adapter.SectionsPagerAdapter
import com.example.mysubmissionapi1.data.GithubUserDetail
import com.example.mysubmissionapi1.databinding.ItemDetailBinding
import com.example.mysubmissionapi1.model.DetailViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserDetail : AppCompatActivity() {

    private lateinit var binding: ItemDetailBinding


    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )

        const val LOGIN = "username"
        const val ID = "Extra_ID"
        const val URL = "avatar_url"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ItemDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val login = intent.getStringExtra(LOGIN)
        val id = intent.getIntExtra(ID, 0)
        val url = intent.getStringExtra(URL)


        val detailViewModel = ViewModelProvider(this)[DetailViewModel::class.java]

        if (login != null) {
            detailViewModel.findDetail(login)
        }

        detailViewModel.detail.observe(this) { detail ->
            setDetailUser(detail)
        }

        detailViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        var _isChecked = false
        CoroutineScope(Dispatchers.IO).launch {
            val count = detailViewModel.ChecktotalFavoriteUser(id)
            withContext(Dispatchers.Main){
                if (count != null){
                    if (count>0){
                        binding.toggleFavorite.isChecked = true
                        _isChecked = true
                    }
                    else {
                        binding.toggleFavorite.isChecked = false
                        _isChecked = false
                    }
                }
            }
        }

        binding.toggleFavorite.setOnClickListener{
            _isChecked = !_isChecked
            if (_isChecked){
                detailViewModel.addUserToFavorite(login.toString(), id, url)
            }
            else {
                detailViewModel.removeUserFromFavorite(id)
            }
            binding.toggleFavorite.isChecked = _isChecked
        }

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        sectionsPagerAdapter.username = login.toString()
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        supportActionBar?.elevation = 0f

    }

    @SuppressLint("SetTextI18n")
    private fun setDetailUser(detail: GithubUserDetail ) {
        binding.name1.text = detail.login
        binding.name2.text = detail.name
        binding.followers.text = detail.followers.toString() + " Followers"
        binding.following.text = detail.following.toString() + " Following"
        Glide.with(this@UserDetail)
            .load(detail.avatarUrl)
            .circleCrop()
            .into(binding.imgItemPhoto)
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}
