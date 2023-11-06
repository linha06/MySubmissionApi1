package com.example.mysubmissionapi1.model

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide.init
import com.example.mysubmissionapi1.data.GithubUserDetail
import com.example.mysubmissionapi1.data.ItemsItem
import com.example.mysubmissionapi1.database.Favorite
import com.example.mysubmissionapi1.database.FavoriteDAO
import com.example.mysubmissionapi1.database.FavoriteRoomDB
import com.example.mysubmissionapi1.retrofit.ApiConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class DetailViewModel(application: Application): AndroidViewModel(application) {

    private var favUserDao : FavoriteDAO?
    private var favUserDb : FavoriteRoomDB?
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        favUserDb = FavoriteRoomDB.getDatabase(application)
        favUserDao = favUserDb?.favoriteDAO()
    }

    private val _detail = MutableLiveData<GithubUserDetail>()
    val detail: LiveData<GithubUserDetail> = _detail

    private val _followers = MutableLiveData<List<ItemsItem>>()
    val followers: LiveData<List<ItemsItem>> = _followers

    private val _following = MutableLiveData<List<ItemsItem>>()
    val following: LiveData<List<ItemsItem>> = _following

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        private const val TAG = "DetailViewModel"
    }

    fun findDetail(User: String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUser(User)
        client.enqueue(object : Callback<GithubUserDetail> {

            override fun onFailure(call: Call<GithubUserDetail>, t: Throwable) {
                _isLoading.value = false
               // Toast.makeText(this@DetailViewModel, "findetail gagal", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<GithubUserDetail>,
                response: Response<GithubUserDetail>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _detail.value = response.body()
                    }
                } else {
                    Log.e(TAG, "onFailure: findDetail fail 2")
                }

            }
        })
    }

    fun getFollowers(follow: String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowers(follow)
        client.enqueue(object : Callback<List<ItemsItem>> {

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: getFollowers fail 1")
            }

            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _followers.value = response.body()
                    }
                } else {
                    Log.e(TAG, "onFailure: getFollowers fail 2")
                }

            }
        })
    }

    fun getFollowing(follow: String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowing(follow)
        client.enqueue(object : Callback<List<ItemsItem>> {

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: getFollowing fail 1")
            }

            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _following.value = response.body()
                    }
                } else {
                    Log.e(TAG, "onFailure: getFollowing fail 2")
                }

            }
        })
    }

    fun addUserToFavorite(username: String, id:Int, avatarUrl: String?){
        executorService.execute {
            CoroutineScope(Dispatchers.IO).launch {
                var user = Favorite(
                    id,
                    username,
                    avatarUrl
                )
                favUserDao?.addToFavorite(user)
            }
        }
    }

    suspend fun ChecktotalFavoriteUser(id: Int) = favUserDao?.totalFavoriteUser(id)

    fun removeUserFromFavorite(id: Int){
        executorService.execute {
            CoroutineScope(Dispatchers.IO).launch {
                favUserDao?.removeFromFavorite(id)
            }
        }
    }
}