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
package com.yourcompany.android.jetreddit.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yourcompany.android.jetreddit.data.database.dao.PostDao
import com.yourcompany.android.jetreddit.data.database.dbmapper.DbMapper
import com.yourcompany.android.jetreddit.data.database.model.PostDbModel
import com.yourcompany.android.jetreddit.domain.model.PostModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RepositoryImpl(private val postDao: PostDao, private val mapper: DbMapper) : Repository {

  private var searchedText = ""

  private val allPostsLiveData: MutableLiveData<List<PostModel>> by lazy {
    MutableLiveData<List<PostModel>>()
  }

  private val ownedPostsLiveData: MutableLiveData<List<PostModel>> by lazy {
    MutableLiveData<List<PostModel>>()
  }

  init {
    initDatabase(this::updatePostLiveData)
  }

  /**
   * Populates database with posts if it is empty.
   */
  private fun initDatabase(postInitAction: () -> Unit) {
    GlobalScope.launch {
      // Prepopulate posts
      val posts = PostDbModel.DEFAULT_POSTS.toTypedArray()
      val dbPosts = postDao.getAllPosts()
      if (dbPosts.isNullOrEmpty()) {
        postDao.insertAll(*posts)
      }

      postInitAction.invoke()
    }
  }

  override fun getAllPosts(): LiveData<List<PostModel>> = allPostsLiveData

  override fun getAllOwnedPosts(): LiveData<List<PostModel>> = ownedPostsLiveData

  override fun getAllSubreddits(searchedText: String): List<String> {
    this.searchedText = searchedText

    if (searchedText.isNotEmpty()) {
      return postDao.getAllSubreddits().filter { it.contains(searchedText) }
    }

    return postDao.getAllSubreddits()
  }

  private fun getAllPostsFromDatabase(): List<PostModel> =
    postDao.getAllPosts().map(mapper::mapPost)

  private fun getAllOwnedPostsFromDatabase(): List<PostModel> =
    postDao.getAllOwnedPosts("johndoe").map(mapper::mapPost)

  override fun insert(post: PostModel) {
    postDao.insert(mapper.mapDbPost(post))
    updatePostLiveData()
  }

  override fun deleteAll() {
    postDao.deleteAll()

    updatePostLiveData()
  }

  private fun updatePostLiveData() {
    allPostsLiveData.postValue(getAllPostsFromDatabase())
    ownedPostsLiveData.postValue(getAllOwnedPostsFromDatabase())
  }
}