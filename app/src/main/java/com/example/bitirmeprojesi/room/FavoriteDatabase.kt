package com.example.bitirmeprojesi.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.bitirmeprojesi.data.model.favorite.FavoriteMovie

@Database(entities = [FavoriteMovie::class], version = 2)
abstract class FavoriteDatabase: RoomDatabase() {
    abstract fun getFavMovieDao() : FavMovieDao
}