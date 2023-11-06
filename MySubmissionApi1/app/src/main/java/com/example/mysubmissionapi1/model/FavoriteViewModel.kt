package com.example.mysubmissionapi1.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.mysubmissionapi1.database.Favorite
import com.example.mysubmissionapi1.database.FavoriteDAO
import com.example.mysubmissionapi1.database.FavoriteRoomDB
import com.example.mysubmissionapi1.repository.FavoriteRepository

class FavoriteViewModel (application: Application): AndroidViewModel(application) {

    private var favUserDao : FavoriteDAO?
    private var favUserDb : FavoriteRoomDB?

    init {
        favUserDb = FavoriteRoomDB.getDatabase(application)
        favUserDao = favUserDb?.favoriteDAO()
    }

    fun getFavoritedUser() : LiveData<List<Favorite>>? {
        return favUserDao?.getFavoritedUser()
    }
}