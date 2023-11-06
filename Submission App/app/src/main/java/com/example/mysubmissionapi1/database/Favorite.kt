package com.example.mysubmissionapi1.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "favorite_user")
@Parcelize
data class Favorite(
    @PrimaryKey
    val id: Int,
    var username: String,
    var avatarUrl: String? = null,
) : Parcelable