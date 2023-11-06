package com.example.mysubmissionapi1.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.mysubmissionapi1.database.Favorite
import com.example.mysubmissionapi1.database.FavoriteDAO
import com.example.mysubmissionapi1.database.FavoriteRoomDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

// TODO : gunakan repository untuk penyimpanan offline nanti
class FavoriteRepository (application: Application) {

    private val mFavoriteDAO: FavoriteDAO
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteRoomDB.getDatabase(application)
        mFavoriteDAO = db.favoriteDAO()
    }

    fun insert(username: String, id:Int, avatarUrl: String?){
        executorService.execute {
            CoroutineScope(Dispatchers.IO).launch {
                var user = Favorite(
                    id,
                    username,
                    avatarUrl
                )
                mFavoriteDAO?.addToFavorite(user)
            }
        }
    }

    fun delete(id: Int){
        executorService.execute {
            CoroutineScope(Dispatchers.IO).launch {
                mFavoriteDAO?.removeFromFavorite(id)
            }
        }
    }

    suspend fun ChecktotalFavoriteUser(id: Int) = mFavoriteDAO?.totalFavoriteUser(id)

    fun getAllFavoriteUser() : LiveData<List<Favorite>>? {
        return mFavoriteDAO?.getFavoritedUser()
    }
}