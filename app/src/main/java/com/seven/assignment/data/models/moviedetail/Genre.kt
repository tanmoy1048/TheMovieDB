package com.seven.assignment.data.models.moviedetail

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

//@Entity(tableName = "Genre")
data class Genre(
//    @PrimaryKey
//    @ColumnInfo(name = "id")
    @SerializedName("id") val id: Int,
    //@ColumnInfo(name = "name")
    @SerializedName("name") val name: String
)