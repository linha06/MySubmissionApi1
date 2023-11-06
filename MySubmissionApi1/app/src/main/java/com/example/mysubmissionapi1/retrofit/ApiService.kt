package com.example.mysubmissionapi1.retrofit

import com.example.mysubmissionapi1.data.GithubResponse
import com.example.mysubmissionapi1.data.GithubUserDetail
import com.example.mysubmissionapi1.data.ItemsItem
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("search/users")    // this called endpoint (untuk merujuk ke query yang dituju)
    fun getGithubUsers(
        @Query("q") q: String       //query, samakan yang didalam "" dengan website, itu untuk key nya
    ) : Call<GithubResponse>

    @GET("users/{username}")
    fun getDetailUser(
        @Path("username") username: String
    ): Call<GithubUserDetail>

    @GET("users/{username}/followers")
    fun getFollowers(
        @Path("username") username: String
    ): Call<List<ItemsItem>>

    @GET("users/{username}/following")
    fun getFollowing(
        @Path("username") username: String
    ): Call<List<ItemsItem>>
}