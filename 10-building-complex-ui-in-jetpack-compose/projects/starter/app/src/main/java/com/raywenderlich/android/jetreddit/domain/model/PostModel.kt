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

      val DEFAULT_POST = PostModel("raywenderlich", "digitalnomad", "My ocean view in Thailand. Laptop pic included!", "","5614", "523", PostType.IMAGE, "4h")
  }
}