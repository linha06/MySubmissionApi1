package com.example.mysubmissionapi1.di

import android.content.Context
import com.example.mysubmissionapi1.database.FavoriteRoomDB
import com.example.mysubmissionapi1.repository.FavoriteRepository
import com.example.mysubmissionapi1.retrofit.ApiConfig
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

// TODO : untuk offline storage
object Injection {
//    fun provideRepository(context: Context): FavoriteRepository {
//        val apiService = ApiConfig.getApiService()
//        val database = FavoriteRoomDB.getDatabase(context)
//        val dao = database.favoriteDAO()
//        val executorService: ExecutorService = Executors.newSingleThreadExecutor()
//        return FavoriteRepository.getInstance(apiService, dao, executorService)
//    }
}