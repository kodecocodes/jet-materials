package com.raywenderlich.android.jetreddit.domain.model

data class PostModel(
    val username: String,
    val subreddit: String,
    val title: String,
    val text: String,
    val likes: String,
    val comments: String,
    val type: PostType,
    val postedTime: String
){

  companion object{

    val DEFAULT_POST = PostModel("raywenderlich", "androiddev", "Check out this new book about Jetpack Compose from Raywenderlich!",
        "Check out this new book about Jetpack Compose from Raywenderlich!", "5614", "523", PostType.TEXT, "4h")

  }
}