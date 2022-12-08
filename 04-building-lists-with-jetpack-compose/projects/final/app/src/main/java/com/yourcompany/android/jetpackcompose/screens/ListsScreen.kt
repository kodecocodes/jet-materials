/*
 * Copyright (c) 2022 Razeware LLC
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

package com.yourcompany.android.jetpackcompose.screens

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.yourcompany.android.jetpackcompose.R
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yourcompany.android.jetpackcompose.router.BackButtonHandler
import com.yourcompany.android.jetpackcompose.router.JetFundamentalsRouter
import com.yourcompany.android.jetpackcompose.router.Screen

private val items = listOf(
  BookCategory(
    R.string.android,
    listOf(
      R.drawable.android_aprentice,
      R.drawable.saving_data_android,
      R.drawable.advanced_architecture_android
    )
  ),
  BookCategory(
    R.string.kotlin,
    listOf(
      R.drawable.kotlin_coroutines,
      R.drawable.kotlin_aprentice
    )
  ),
  BookCategory(
    R.string.swift,
    listOf(
      R.drawable.combine,
      R.drawable.rx_swift,
      R.drawable.swift_apprentice,
    )
  ),
  BookCategory(
    R.string.ios,
    listOf(
      R.drawable.core_data,
      R.drawable.ios_apprentice,
    )
  )
)

@Composable
fun ListScreen() {
  MyList()

  BackButtonHandler {
    JetFundamentalsRouter.navigateTo(Screen.Navigation)
  }
}

@Composable
fun MyList() {
  LazyColumn {
    items(items) { item -> ListItem(item) }
  }
}

@Composable
fun ListItem(bookCategory: BookCategory, modifier: Modifier = Modifier) {
  Column(modifier = Modifier.padding(8.dp)) {
    Text(
      text = stringResource(bookCategory.categoryResourceId),
      fontSize = 22.sp,
      fontWeight = FontWeight.Bold,
      color = colorResource(id = R.color.colorPrimary)
    )
    Spacer(modifier = modifier.height(8.dp))

    LazyRow {
      items(bookCategory.bookImageResources) { items ->
        BookImage(items)
      }
    }
  }
}

@Composable
fun BookImage(imageResource: Int) {
  Image(
    modifier = Modifier.size(170.dp, 200.dp),
    painter = painterResource(id = imageResource),
    contentScale = ContentScale.Fit,
    contentDescription = stringResource(R.string.book_image)
  )
}

data class BookCategory(@StringRes val categoryResourceId: Int, val bookImageResources: List<Int>)