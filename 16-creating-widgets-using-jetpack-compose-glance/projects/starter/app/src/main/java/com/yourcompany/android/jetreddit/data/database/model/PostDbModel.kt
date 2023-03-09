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
package com.yourcompany.android.jetreddit.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

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
    @ColumnInfo(name = "is_saved") val isSaved: Boolean
) {

    companion object {

        val DEFAULT_POSTS = listOf(
            PostDbModel(
                1,
                "johndoe",
                "androiddev",
                "Check out this new book about Jetpack Compose from Kodeco!",
                "Check out this new book about Jetpack Compose from Kodeco!",
                5614,
                523,
                0,
                System.currentTimeMillis(),
                false
            ),
            PostDbModel(
                2,
                "pro_dev",
                "digitalnomad",
                "My ocean view in Thailand.",
                "",
                2314,
                23,
                1,
                System.currentTimeMillis(),
                false
            ),
            PostDbModel(
                3,
                "johndoe",
                "programming",
                "Check out this new book about Jetpack Compose from Kodeco!",
                "Check out this new book about Jetpack Compose from Kodeco!",
                5214,
                423,
                0,
                System.currentTimeMillis(),
                false
            ),
            PostDbModel(
                4,
                "johndoe",
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
                5,
                "ps_guy",
                "playstation",
                "My PS5 just arrived!",
                "",
                56231,
                823,
                0,
                System.currentTimeMillis(),
                false
            )
        )
    }
}