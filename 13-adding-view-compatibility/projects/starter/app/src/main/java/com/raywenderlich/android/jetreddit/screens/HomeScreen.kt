/*
 * Copyright (c) 2020 Razeware LLC
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
package com.raywenderlich.android.jetreddit.screens

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.raywenderlich.android.jetreddit.components.ImagePost
import com.raywenderlich.android.jetreddit.components.TextPost
import com.raywenderlich.android.jetreddit.domain.model.PostModel
import com.raywenderlich.android.jetreddit.domain.model.PostType
import com.raywenderlich.android.jetreddit.viewmodel.MainViewModel

import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import java.util.*
import kotlin.concurrent.schedule
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Alignment
import com.raywenderlich.android.jetreddit.components.JoinedToast

@ExperimentalAnimationApi
@Composable
fun HomeScreen(viewModel: MainViewModel) {
  val posts: List<PostModel>
      by viewModel.allPosts.observeAsState(listOf())

  var visible by remember { mutableStateOf(false) }

  val onJoinClickAction: (Boolean) -> Unit = { joined ->
    visible = joined
    if (visible) {
      Timer().schedule(3000) {
        visible = false
      }
    }
  }

  Box(modifier = Modifier.fillMaxSize()) {
    LazyColumnFor(
        items = posts,
        modifier = Modifier
            .background(color = MaterialTheme.colors.secondary)
    ) {
      if (it.type == PostType.TEXT) {
        TextPost(it, onJoinButtonClick = onJoinClickAction)
      } else {
        ImagePost(it, onJoinButtonClick = onJoinClickAction)
      }
      Spacer(modifier = Modifier.height(6.dp))
    }

    Box(modifier = Modifier
        .align(Alignment.BottomCenter)
        .padding(bottom = 16.dp)) {
      JoinedToast(visible = visible)
    }
  }
}