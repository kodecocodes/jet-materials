package com.raywenderlich.android.jetreddit.data.database.dbmapper

import com.raywenderlich.android.jetreddit.data.database.model.PostDbModel
import com.raywenderlich.android.jetreddit.domain.model.PostModel

interface DbMapper {

  fun mapPost(dbPostDbModel: PostDbModel): PostModel

  fun mapDbPost(postModel: PostModel): PostDbModel
}