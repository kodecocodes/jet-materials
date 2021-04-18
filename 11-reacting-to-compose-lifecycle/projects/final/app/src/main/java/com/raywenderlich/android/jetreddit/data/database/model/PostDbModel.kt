package com.raywenderlich.android.jetreddit.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.raywenderlich.android.jetreddit.R

@Entity
data class PostDbModel(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Long?,
    @ColumnInfo(name = "username") val username: String,
    @ColumnInfo(name = "subreddit") val subreddit: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "text") val text: String,
    @ColumnInfo(name = "likes") val likes: Int,
    @ColumnInfo(name = "comments") val comments: Int,
    @ColumnInfo(name = "type") val type: Int,
    @ColumnInfo(name = "date_posted") val datePosted: Long,
    @ColumnInfo(name = "is_saved") val isSaved: Boolean,
    @ColumnInfo(name = "image") val image: Int? = null
) {

  companion object {

    val DEFAULT_POSTS = listOf(
        PostDbModel(
            1,
            "raywenderlich",
            "androiddev",
            "Check out this new book about Jetpack Compose from raywenderlich.com!",
            "Check out this new book about Jetpack Compose from raywenderlich.com!",
            5614,
            523,
            0,
            System.currentTimeMillis(),
            false,
            image = R.drawable.compose_course
        ),
        PostDbModel(
            2,
            "pro_dev",
            "digitalnomad",
            "My ocean view in Thailand.",
            "",
            2314,
            23,
            1, System.currentTimeMillis(),
            false,
            image = R.drawable.thailand
        ),
        PostDbModel(
            3,
            "raywenderlich",
            "programming",
            "Check out this new book about Jetpack Compose from raywenderlich.com!",
            "Check out this new book about Jetpack Compose from raywenderlich.com!",
            5214,
            423,
            0,
            System.currentTimeMillis(),
            false
        ),
        PostDbModel(
            4,
            "raywenderlich",
            "puppies",
            "My puppy running around the house looks so cute!",
            "My puppy running around the house looks so cute!",
            25315,
            1362,
            0,
            System.currentTimeMillis(),
            false
        ),
        PostDbModel(
            5, "ps_guy", "playstation", "My PS5 just arrived!",
            "", 56231, 823, 0, System.currentTimeMillis(), false
        )
    )
  }
}