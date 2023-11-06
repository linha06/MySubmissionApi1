package com.example.mysubmissionapi1.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mysubmissionapi1.adapter.NameAdapter
import com.example.mysubmissionapi1.data.ItemsItem
import com.example.mysubmissionapi1.databinding.FragmentFollowBinding
import com.example.mysubmissionapi1.model.DetailViewModel


class FollowFragment : Fragment() {

    private lateinit var binding: FragmentFollowBinding

    companion object {
        const val ARG_POSITION = "position"
        const val ARG_USERNAME = "username"
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View {

        binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.followRR.layoutManager = LinearLayoutManager(UserDetail())

        var position = arguments?.getInt(ARG_POSITION, 0)
        var username = arguments?.getString(ARG_USERNAME)

        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(ARG_USERNAME)
        }

        val detailViewModel = ViewModelProvider(this)[DetailViewModel::class.java]

        if (position == 1){
            detailViewModel.isLoading.observe(viewLifecycleOwner){
                showLoading(it)
            }
            detailViewModel.getFollowers(username.toString())
            detailViewModel.followers.observe(viewLifecycleOwner){
                setFollow(it)
            }
        } else
            detailViewModel.isLoading.observe(viewLifecycleOwner){
                showLoading(it)
            }
        detailViewModel.getFollowing(username.toString())
        detailViewModel.following.observe(viewLifecycleOwner){
            setFollow(it)
        }
    }

    private fun setFollow(items: List<ItemsItem>){
        val adapter = NameAdapter()
        adapter.submitList(items)
        binding.followRR.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}