package com.yourcompany.android.jetreddit.data.repository

import androidx.lifecycle.LiveData
import com.yourcompany.android.jetreddit.domain.model.PostModel

interface Repository {

  fun getAllPosts(): LiveData<List<PostModel>>

  fun getAllOwnedPosts(): LiveData<List<PostModel>>

  fun insert(post: PostModel)

  fun deleteAll()
}