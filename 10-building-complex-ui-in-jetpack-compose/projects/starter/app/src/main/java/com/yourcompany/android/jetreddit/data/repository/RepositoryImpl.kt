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