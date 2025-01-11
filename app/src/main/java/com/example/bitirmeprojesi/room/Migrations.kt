package com.example.bitirmeprojesi.room

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
// Migration object to add year and category columns to the database
object Migration_1_to_2 : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE fav_movies ADD COLUMN year INTEGER NOT NULL DEFAULT 0")
        database.execSQL("ALTER TABLE fav_movies ADD COLUMN category TEXT NOT NULL DEFAULT ''")
    }
}