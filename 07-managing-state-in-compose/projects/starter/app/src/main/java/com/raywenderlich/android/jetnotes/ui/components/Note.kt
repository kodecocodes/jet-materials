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
package com.raywenderlich.android.jetnotes.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.raywenderlich.android.jetnotes.theme.rwGreen

@Composable
fun Note() {
  val backgroundShape: Shape = RoundedCornerShape(4.dp)

  Row(
    modifier = Modifier
      .padding(8.dp)
      .shadow(1.dp, backgroundShape)
      .fillMaxWidth()
      .heightIn(min = 64.dp)
      .background(Color.White, backgroundShape)
  ) {
    NoteColor(
      modifier = Modifier
        .align(Alignment.CenterVertically)
        .padding(start = 16.dp, end = 16.dp),
      color = rwGreen,
      size = 40.dp,
      border = 1.dp
    )
    Column(
      modifier = Modifier
        .weight(1f)
        .align(Alignment.CenterVertically)
    ) {
      Text(
        text = "Title",
        color = Color.Black,
        maxLines = 1,
        style = TextStyle(
          fontWeight = FontWeight.Normal,
          fontSize = 16.sp,
          letterSpacing = 0.15.sp
        )
      )
      Text(
        text = "Content",
        color = Color.Black.copy(alpha = 0.75f),
        maxLines = 1,
        style = TextStyle(
          fontWeight = FontWeight.Normal,
          fontSize = 14.sp,
          letterSpacing = 0.25.sp
        )
      )
    }
    Checkbox(
      checked = false,
      onCheckedChange = { },
      modifier = Modifier
        .padding(16.dp)
        .align(Alignment.CenterVertically)
    )
  }
}

@Preview
@Composable
private fun NotePreview() {
  Note()
}