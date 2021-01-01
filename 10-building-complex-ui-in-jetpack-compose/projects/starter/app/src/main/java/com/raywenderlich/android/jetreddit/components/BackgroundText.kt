package com.raywenderlich.android.jetreddit.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BackgroundText(text: String) {
  Text(
    fontWeight = FontWeight.Medium,
    text = text,
    fontSize = 10.sp,
    color = Color.DarkGray,
    modifier = Modifier
      .background(color = MaterialTheme.colors.secondary)
      .padding(start = 16.dp, top = 4.dp, bottom = 4.dp)
      .fillMaxWidth()
  )
}