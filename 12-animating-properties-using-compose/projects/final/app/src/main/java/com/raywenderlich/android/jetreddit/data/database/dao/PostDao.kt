package com.raywenderlich.android.jetreddit.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.raywenderlich.android.jetreddit.data.database.model.PostDbModel

/**
 * Dao for managing Post table in the database.
 */
@Dao
interface PostDao {

  @Query("SELECT * FROM PostDbModel")
  fun getAllPosts(): List<PostDbModel>

  @Query("SELECT * FROM PostDbModel WHERE username = :username")
  fun getAllOwnedPosts(username: String): List<PostDbModel>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insert(postDbModel: PostDbModel)

  @Query("DELETE FROM PostDbModel")
  fun deleteAll()

  @Insert
  fun insertAll(vararg PostDbModels: PostDbModel)
}