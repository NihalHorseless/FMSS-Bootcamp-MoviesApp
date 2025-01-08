package com.example.bitirmeprojesi.di

import android.content.Context
import androidx.room.Room
import com.example.bitirmeprojesi.data.datasource.CartDataSource
import com.example.bitirmeprojesi.data.datasource.MoviesDataSource
import com.example.bitirmeprojesi.data.repo.GeneralRepository
import com.example.bitirmeprojesi.data.retrofit.ApiUtils
import com.example.bitirmeprojesi.data.retrofit.MovieDao
import com.example.bitirmeprojesi.room.FavMovieDao
import com.example.bitirmeprojesi.room.FavoriteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideGeneralRepository(moviesDataSource: MoviesDataSource,cartDataSource: CartDataSource) : GeneralRepository {
        return GeneralRepository(moviesDataSource = moviesDataSource,cartDataSource = cartDataSource)
    }

    @Provides
    @Singleton
    fun provideMoviesDataSource(movieDao: MovieDao,favMovieDao: FavMovieDao) : MoviesDataSource {
        return MoviesDataSource(movieDao = movieDao, favoriteMovieDao = favMovieDao)
    }

    @Provides
    @Singleton
    fun provideCartDataSource(movieDao: MovieDao,favMovieDao: FavMovieDao) : CartDataSource {
        return CartDataSource(movieDao = movieDao)
    }

    @Provides
    @Singleton
    fun provideMovieDao():MovieDao {
        return ApiUtils.getMovieDao()
    }
    @Provides
    @Singleton
    fun provideFavMovieDao(@ApplicationContext context: Context): FavMovieDao {
        val database =
            Room.databaseBuilder(context = context, klass =  FavoriteDatabase::class.java, name = "fav_movies")
                .build()

        return database.getFavMovieDao()

    }
}