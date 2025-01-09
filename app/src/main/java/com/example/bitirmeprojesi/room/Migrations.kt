package com.example.bitirmeprojesi.room

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

object Migration_1_to_2 : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // Add new columns for year and category
        database.execSQL("ALTER TABLE fav_movies ADD COLUMN year INTEGER NOT NULL DEFAULT 0")
        database.execSQL("ALTER TABLE fav_movies ADD COLUMN category TEXT NOT NULL DEFAULT ''")
    }
}