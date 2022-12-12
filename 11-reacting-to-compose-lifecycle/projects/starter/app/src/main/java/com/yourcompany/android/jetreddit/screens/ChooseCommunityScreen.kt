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

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yourcompany.android.jetreddit.R
import com.yourcompany.android.jetreddit.viewmodel.MainViewModel

private const val SEARCH_DELAY_MILLIS = 300L

private val defaultCommunities = listOf("kodeco", "androiddev", "puppies")

@Composable
fun ChooseCommunityScreen(
  viewModel: MainViewModel,
  modifier: Modifier = Modifier,
  onBackSelected: () -> Unit
) {
  //TODO Add your code here
}

@Composable
fun SearchedCommunities(
  communities: List<String>,
  viewModel: MainViewModel?,
  modifier: Modifier = Modifier
) {
  //TODO Add your code here
}

@Composable
fun ChooseCommunityTopBar(
  modifier: Modifier = Modifier,
  onBackSelected: () -> Unit
) {

  val colors = MaterialTheme.colors

  TopAppBar(
    title = {
      Text(
        fontSize = 16.sp,
        text = stringResource(R.string.choose_community),
        color = colors.primaryVariant
      )
    },
    navigationIcon = {
      IconButton(
        onClick = { onBackSelected.invoke() }
      ) {
        Icon(
          imageVector = Icons.Default.Close,
          tint = colors.primaryVariant,
          contentDescription = stringResource(id = R.string.close)
        )
      }
    },
    backgroundColor = colors.primary,
    elevation = 0.dp,
    modifier = modifier
      .height(48.dp)
      .background(Color.Blue)
  )
}
