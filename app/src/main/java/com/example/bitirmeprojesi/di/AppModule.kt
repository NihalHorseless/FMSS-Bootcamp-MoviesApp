package com.example.bitirmeprojesi.di

import com.example.bitirmeprojesi.data.datasource.CartDataSource
import com.example.bitirmeprojesi.data.datasource.MoviesDataSource
import com.example.bitirmeprojesi.data.repo.GeneralRepository
import com.example.bitirmeprojesi.data.retrofit.ApiUtils
import com.example.bitirmeprojesi.data.retrofit.MovieDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
    fun provideMoviesDataSource(movieDao: MovieDao) : MoviesDataSource {
        return MoviesDataSource(movieDao)
    }

    @Provides
    @Singleton
    fun provideCartDataSource(movieDao: MovieDao) : CartDataSource {
        return CartDataSource(movieDao)
    }

    @Provides
    @Singleton
    fun provideMovieDao():MovieDao {
        return ApiUtils.getMovieDao()
    }
}