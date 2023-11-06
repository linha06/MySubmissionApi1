package com.example.mysubmissionapi1.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Favorite::class], version = 1)
abstract class FavoriteRoomDB : RoomDatabase() {

    abstract fun favoriteDAO() : FavoriteDAO

    companion object {
        @Volatile
        private var INSTANCE: FavoriteRoomDB? = null

        @JvmStatic
        fun getDatabase(context: Context): FavoriteRoomDB {
            if (INSTANCE == null) {
                synchronized(FavoriteRoomDB::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        FavoriteRoomDB::class.java, "favorite_database")
                        .build()
                }
            }
            return INSTANCE as FavoriteRoomDB
        }
    }
}