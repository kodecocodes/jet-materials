package com.yourcompany.android.jetreddit.factory

import com.yourcompany.android.jetreddit.domain.model.PostModel
import com.yourcompany.android.jetreddit.domain.model.PostType
import java.util.UUID
import java.util.UUID.randomUUID
import java.util.concurrent.ThreadLocalRandom

object PostDataFactory {

    fun randomString() = UUID.randomUUID().toString()

    fun randomInt(): Int {
        return ThreadLocalRandom.current().nextInt(0, 1000 + 1)
    }

    fun createPosts(count: Int = 10): List<PostModel> {
        val list = mutableListOf<PostModel>()
        repeat(count) {
            list.add(if (it % 2 == 0) PostModel.DEFAULT_POST else PostModel.EMPTY)
        }
        return list
    }

    fun createCommunities(count: Int = 10): List<String> {
        val list = mutableListOf<String>()
        repeat(count) {
            list.add(randomString())
        }
        return list
    }

    fun createPost() = PostModel(
        randomString(),
        randomString(),
        randomString(),
        randomString(),
        randomInt().toString(),
        randomString(),
        PostType.TEXT,
        randomString(),
        null
    )
}