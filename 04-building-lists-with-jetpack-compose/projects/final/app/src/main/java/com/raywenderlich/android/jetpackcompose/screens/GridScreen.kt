/*
 * Copyright (c) 2021 Razeware LLC
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

package com.raywenderlich.android.jetpackcompose.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.raywenderlich.android.jetpackcompose.R
import com.raywenderlich.android.jetpackcompose.router.BackButtonHandler
import com.raywenderlich.android.jetpackcompose.router.JetFundamentalsRouter
import com.raywenderlich.android.jetpackcompose.router.Screen
import kotlin.math.ceil

private val items = listOf(
  Icons.Filled.Check,
  Icons.Filled.Close,
  Icons.Filled.ThumbUp,
  Icons.Filled.Build,
  Icons.Filled.Delete,
  Icons.Filled.Home,
  Icons.Filled.Close,
  Icons.Filled.ThumbUp,
  Icons.Filled.Build,
  Icons.Filled.ThumbUp,
)

data class IconResource(val imageVector: ImageVector, val isVisible: Boolean)

@ExperimentalFoundationApi
@Composable
fun GridScreen() {
  LazyVerticalGrid(
    modifier = Modifier.fillMaxSize(),
    cells = GridCells.Fixed(3),
    content = {
      items(items) { item ->
        GridIcon(IconResource(item, true))
      }
    }
  )

  BackButtonHandler {
    JetFundamentalsRouter.navigateTo(Screen.Navigation)
  }
}

@Composable
fun GridView(columnCount: Int) {
  val itemSize = items.size
  val rowCount = ceil(itemSize.toFloat() / columnCount).toInt()
  val gridItems = mutableListOf<List<IconResource>>()
  var position = 0

  for (i in 0 until rowCount) {
    val rowItem = mutableListOf<IconResource>()
    for (j in 0 until columnCount) {
      if (position.inc() <= itemSize) {
        rowItem.add(IconResource(items[position++], true))
      }
    }
    // here
    val itemsToFill = columnCount - rowItem.size

    for (j in 0 until itemsToFill) {
      rowItem.add(IconResource(Icons.Filled.Delete, false))
    }
    gridItems.add(rowItem)
  }
  // here
  LazyColumn(modifier = Modifier.fillMaxSize()) {
    items(gridItems) { items ->
      RowItem(items)
    }
  }
}

@Composable
fun RowItem(rowItems: List<IconResource>) {
  Row {
    for (element in rowItems)
      GridIcon(element)
  }
}

@Composable
fun RowScope.GridIcon(iconResource: IconResource) {
  val color = if (iconResource.isVisible)
    colorResource(R.color.colorPrimary)
  else Color.Transparent

  Icon(
    imageVector = iconResource.imageVector,
    tint = color,
    contentDescription = stringResource(R.string.grid_icon),
    modifier = Modifier
      .size(80.dp, 80.dp)
      .weight(1f)
  )
}

@Composable
fun GridIcon(iconResource: IconResource) {
  val color = if (iconResource.isVisible)
    colorResource(R.color.colorPrimary)
  else Color.Transparent

  Icon(
    imageVector = iconResource.imageVector,
    tint = color,
    contentDescription = stringResource(R.string.grid_icon),
    modifier = Modifier
      .size(80.dp, 80.dp)
  )
}