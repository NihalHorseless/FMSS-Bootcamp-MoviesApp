package com.example.bitirmeprojesi.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.bitirmeprojesi.data.model.favorite.FavoriteMovie

@Dao
interface FavMovieDao {
    @Query("SELECT * FROM fav_movies")
    suspend fun getAll(): List<FavoriteMovie>

    @Query("DELETE FROM fav_movies WHERE fav_id = :movieId")
    suspend fun deleteById(movieId: Int)

    @Update
    suspend fun updateMovie(favMovie: FavoriteMovie)

    @Insert
    suspend fun addMovieToFav(favMovie: FavoriteMovie)

    @Query("SELECT EXISTS(SELECT 1 FROM fav_movies WHERE fav_id = :movieId)")
    suspend fun doesFavMovieExist(movieId: Int): Boolean
}