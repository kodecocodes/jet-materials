package com.raywenderlich.android.jetreddit.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.raywenderlich.android.jetreddit.data.database.dao.PostDao
import com.raywenderlich.android.jetreddit.data.database.model.PostDbModel

/**
 * App's database.
 *
 * It contains a table for posts
 */
@Database(entities = [PostDbModel::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

  companion object {
    const val DATABASE_NAME = "jet-reddit-database"
  }

  abstract fun postDao(): PostDao
}