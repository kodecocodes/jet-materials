/*
 * Copyright (c) 2021 Kodeco Inc.
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
package com.yourcompany.android.jetnotes.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yourcompany.android.jetnotes.domain.model.NoteModel
import com.yourcompany.android.jetnotes.util.fromHex

@ExperimentalMaterialApi
@Composable
fun Note(
  modifier: Modifier = Modifier,
  note: NoteModel,
  onNoteClick: (NoteModel) -> Unit = {},
  onNoteCheckedChange: (NoteModel) -> Unit = {},
  isSelected: Boolean
) {
  val background = if (isSelected)
    Color.LightGray
  else
    MaterialTheme.colors.surface

  Card(
    shape = RoundedCornerShape(4.dp),
    modifier = modifier
      .padding(8.dp)
      .fillMaxWidth(),
    backgroundColor = background
  ) {
    ListItem(
      text = { Text(text = note.title, maxLines = 1) },
      secondaryText = {
        Text(text = note.content, maxLines = 1)
      },
      icon = {
        NoteColor(
          color = Color.fromHex(note.color.hex),
          size = 40.dp,
          border = 1.dp
        )
      },
      trailing = {
        if (note.isCheckedOff != null) {
          Checkbox(
            checked = note.isCheckedOff,
            onCheckedChange = { isChecked ->
              val newNote = note.copy(isCheckedOff = isChecked)
              onNoteCheckedChange.invoke(newNote)
            },
            modifier = Modifier.padding(start = 8.dp)
          )
        }
      },
      modifier = Modifier.clickable {
        onNoteClick.invoke(note)
      }
    )
  }
}

@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
private fun NotePreview() {
  Note(note = NoteModel(1, "Note 1", "Content 1", null), isSelected = false)
}