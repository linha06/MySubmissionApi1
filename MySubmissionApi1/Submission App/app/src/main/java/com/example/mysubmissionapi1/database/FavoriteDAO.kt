package com.example.mysubmissionapi1.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FavoriteDAO {
    @Insert
    suspend fun addToFavorite(favorite: Favorite)

    @Query("SELECT * FROM favorite_user")
    fun getFavoritedUser(): LiveData<List<Favorite>>

    @Query("SELECT count(*) FROM favorite_user WHERE favorite_user.id = :id")
    suspend fun totalFavoriteUser(id: Int): Int

    @Query("DELETE FROM favorite_user WHERE favorite_user.id = :id")
    suspend fun removeFromFavorite(id: Int): Int
}