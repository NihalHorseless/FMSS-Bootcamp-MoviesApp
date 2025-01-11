package com.example.bitirmeprojesi.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.bitirmeprojesi.data.model.favorite.FavoriteMovie

@Database(entities = [FavoriteMovie::class], version = 2) // Database version is updated to 2 for migration
abstract class FavoriteDatabase: RoomDatabase() {
    abstract fun getFavMovieDao() : FavMovieDao
}