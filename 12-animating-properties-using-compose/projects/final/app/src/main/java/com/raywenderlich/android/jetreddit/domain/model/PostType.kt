package com.raywenderlich.android.jetreddit.domain.model

enum class PostType(val type: Int) {

  TEXT(0),
  IMAGE(1);

  companion object {

    fun fromType(type: Int): PostType {
      return if (type == TEXT.type) {
        TEXT
      } else {
        IMAGE
      }
    }
  }
}