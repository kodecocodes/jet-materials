package com.raywenderlich.android.jetnotes.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun NoteColor(
  color: Color,
  size: Dp,
  border: Dp,
  modifier: Modifier = Modifier
) {
  Box(
    modifier = modifier
      .size(size)
      .clip(CircleShape)
      .background(color)
      .border(
        BorderStroke(
          border,
          SolidColor(Color.Black)
        ),
        CircleShape
      )
  )
}

@Preview
@Composable
fun NoteColorPreview() {
  NoteColor(
    color = Color.Red,
    size = 40.dp,
    border = 2.dp
  )
}