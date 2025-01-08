package com.example.bitirmeprojesi.data.model.favorite

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fav_movies")
data class FavoriteMovie(
    @PrimaryKey
    var fav_id: Int = 0,
    @ColumnInfo(name = "name")
    var name: String = "",
    @ColumnInfo(name = "image")
    var image: String = "",
    @ColumnInfo(name = "rating")
    var rating: Double = 1.0
    ) {

}