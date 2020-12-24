package com.raywenderlich.android.jetreddit.data.database.dbmapper

import com.raywenderlich.android.jetreddit.data.database.model.PostDbModel
import com.raywenderlich.android.jetreddit.domain.model.PostModel
import com.raywenderlich.android.jetreddit.domain.model.PostType
import java.util.concurrent.TimeUnit

class DbMapperImpl : DbMapper {

  override fun mapPost(dbPostDbModel: PostDbModel): PostModel {
    with(dbPostDbModel) {
      return PostModel(username, subreddit, title, text, likes.toString(), comments.toString(), PostType.fromType(type), getPostedDate(datePosted))
    }
  }

  override fun mapDbPost(postModel: PostModel): PostDbModel {
    with(postModel) {
      return PostDbModel(null, "raywenderlich", subreddit, title, text, 0, 0, type.type, System.currentTimeMillis(), false)
    }
  }

  private fun getPostedDate(date: Long): String {
    val hoursPassed = TimeUnit.HOURS.convert(System.currentTimeMillis() - date, TimeUnit.MILLISECONDS)
    if (hoursPassed > 24) {
      val daysPassed = TimeUnit.DAYS.convert(hoursPassed, TimeUnit.HOURS)

      if (daysPassed > 30) {
        if (daysPassed > 365) {
          return (daysPassed / 365).toString() + "y"
        }
        return (daysPassed / 30).toString() + "mo"

      }
      return daysPassed.toString() + "d"
    }

    return hoursPassed.inc().toString() + "h"
  }
}