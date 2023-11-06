package com.example.mysubmissionapi1.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mysubmissionapi1.data.GithubResponse
import com.example.mysubmissionapi1.data.ItemsItem
import com.example.mysubmissionapi1.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel: ViewModel() {
    private val _items = MutableLiveData<List<ItemsItem>>()
    val items: LiveData<List<ItemsItem>> = _items

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object{
        private const val TAG = "MainViewModel"
        private const val LOGIN_ID = "sidiqpermana"
    }

    init {
        findName()
    }

    private fun findName() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getGithubUsers(LOGIN_ID)
        client.enqueue(object : Callback<GithubResponse> {
            override fun onResponse(
                call: Call<GithubResponse>,
                response: Response<GithubResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _items.value = response.body()?.items
                    }
                } else {
                    Log.e(TAG, "onFailure: findName fail 1")
                }
            }
            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: findName fail 1")
            }
        })
    }

    fun findUsername(query: String){
        val client = ApiConfig.getApiService().getGithubUsers(query)
        client.enqueue(object : Callback<GithubResponse> {
            override fun onResponse(
                call: Call<GithubResponse>,
                response: Response<GithubResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _items.value = response.body()?.items
                    }
                } else {
                    Log.e(TAG, "onFailure: findUsernme fail 1")
                }
            }
            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: findUsernme fail 2")
            }
        })
    }
}