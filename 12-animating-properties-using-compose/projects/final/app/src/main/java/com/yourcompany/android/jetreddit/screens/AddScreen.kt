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
package com.yourcompany.android.jetreddit.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.yourcompany.android.jetreddit.R
import com.yourcompany.android.jetreddit.domain.model.PostModel
import com.yourcompany.android.jetreddit.routing.Screen
import com.yourcompany.android.jetreddit.viewmodel.MainViewModel

@Composable
fun AddScreen(viewModel: MainViewModel, navHostController: NavHostController) {

  val selectedCommunity: String by viewModel.selectedCommunity.observeAsState("")

  var post by remember { mutableStateOf(PostModel.EMPTY) }

  Column(modifier = Modifier.fillMaxSize()) {

    CommunityPicker(selectedCommunity, navHostController)

    TitleTextField(post.title) { newTitle -> post = post.copy(title = newTitle) }

    BodyTextField(post.text) { newContent -> post = post.copy(text = newContent) }

    AddPostButton(selectedCommunity.isNotEmpty() && post.title.isNotEmpty()) {
      viewModel.savePost(post)
      navHostController.popBackStack()
    }
  }
}

/**
 * Input view for the post title
 */
@Composable
private fun TitleTextField(text: String, onTextChange: (String) -> Unit) {
  val activeColor = MaterialTheme.colors.onSurface

  TextField(
    value = text,
    onValueChange = onTextChange,
    label = { Text(stringResource(R.string.title)) },
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 8.dp),
    colors = TextFieldDefaults.outlinedTextFieldColors(
      focusedBorderColor = activeColor,
      focusedLabelColor = activeColor,
      cursorColor = activeColor,
      backgroundColor = MaterialTheme.colors.surface
    )
  )
}

/**
 * Input view for the post body
 */
@Composable
private fun BodyTextField(text: String, onTextChange: (String) -> Unit) {
  val activeColor = MaterialTheme.colors.onSurface

  TextField(
    value = text,
    onValueChange = onTextChange,
    label = { Text(stringResource(R.string.body_text)) },
    modifier = Modifier
      .fillMaxWidth()
      .heightIn(max = 240.dp)
      .padding(horizontal = 8.dp)
      .padding(top = 16.dp),
    colors = TextFieldDefaults.outlinedTextFieldColors(
      focusedBorderColor = activeColor,
      focusedLabelColor = activeColor,
      cursorColor = activeColor,
      backgroundColor = MaterialTheme.colors.surface
    )
  )
}

/**
 * Input view for the post body
 */
@Composable
private fun AddPostButton(isEnabled: Boolean, onSaveClicked: () -> Unit) {
  Button(
    onClick = onSaveClicked,
    enabled = isEnabled,
    content = {
      Text(
        text = stringResource(R.string.save_post),
        color = MaterialTheme.colors.onSurface
      )
    },
    modifier = Modifier
      .fillMaxWidth()
      .heightIn(max = 240.dp)
      .padding(horizontal = 8.dp)
      .padding(top = 16.dp),
  )
}

@Composable
private fun CommunityPicker(
  selectedCommunity: String,
  navHostController: NavHostController
) {

  val selectedText =
    if (selectedCommunity.isEmpty()) stringResource(R.string.choose_community) else selectedCommunity

  Row(
    modifier = Modifier
      .fillMaxWidth()
      .heightIn(max = 240.dp)
      .padding(horizontal = 8.dp)
      .padding(top = 16.dp)
      .clickable {
        navHostController.navigate(Screen.ChooseCommunity.route)
      },
  ) {
    Image(
      bitmap = ImageBitmap.imageResource(id = R.drawable.subreddit_placeholder),
      contentDescription = stringResource(id = R.string.subreddits),
      modifier = Modifier
        .size(24.dp)
        .clip(CircleShape)
    )

    Text(
      text = selectedText,
      modifier = Modifier.padding(start = 8.dp)
    )
  }
}