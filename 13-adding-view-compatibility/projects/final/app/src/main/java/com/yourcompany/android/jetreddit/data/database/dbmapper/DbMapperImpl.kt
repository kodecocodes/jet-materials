/*
 * Copyright (c) 2022 Kodeco Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * Notwithstanding the foregoing, you may not use, copy, modify, merge, publish,
 * distribute, sublicense, create a derivative work, and/or sell copies of the
 * Software in any work that is designed, intended, or marketed for pedagogical or
 * instructional purposes related to programming, coding, application development,
 * or information technology.  Permission for such use, copying, modification,
 * merger, publication, distribution, sublicensing, creation of derivative works,
 * or sale is expressly withheld.
 *
 * This project and source code may use libraries or frameworks that are
 * released under various Open-Source licenses. Use of those libraries and
 * frameworks are governed by their own individual licenses.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.yourcompany.android.jetreddit.data.database.dbmapper

import com.yourcompany.android.jetreddit.R
import com.yourcompany.android.jetreddit.data.database.model.PostDbModel
import com.yourcompany.android.jetreddit.data.database.model.PostDbModel.Companion.DEFAULT_POSTS
import com.yourcompany.android.jetreddit.domain.model.PostModel
import com.yourcompany.android.jetreddit.domain.model.PostType
import java.util.concurrent.TimeUnit

class DbMapperImpl : DbMapper {

  override fun mapPost(dbPostDbModel: PostDbModel): PostModel {
    with(dbPostDbModel) {
      return PostModel(
        username,
        subreddit,
        title,
        text,
        likes.toString(),
        comments.toString(),
        PostType.fromType(type),
        getPostedDate(datePosted),
        mapImage(dbPostDbModel.id!!)
      )
    }
  }

  private fun mapImage(id: Long): Int? =
    if (id == DEFAULT_POSTS[1].id) {
      R.drawable.thailand
    } else {
      null
    }


  override fun mapDbPost(postModel: PostModel): PostDbModel {
    with(postModel) {
      return PostDbModel(
        null,
        "johndoe",
        subreddit,
        title,
        text,
        0,
        0,
        type.type,
        System.currentTimeMillis(),
        false
      )
    }
  }

  private fun getPostedDate(date: Long): String {
    val hoursPassed =
      TimeUnit.HOURS.convert(System.currentTimeMillis() - date, TimeUnit.MILLISECONDS)
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