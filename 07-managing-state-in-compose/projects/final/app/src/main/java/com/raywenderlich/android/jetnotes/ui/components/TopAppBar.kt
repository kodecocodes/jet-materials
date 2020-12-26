package com.raywenderlich.android.jetnotes.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.primarySurface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.raywenderlich.android.jetnotes.theme.JetNotesTheme

@Composable
fun TopAppBar(
  title: String,
  icon: ImageVector,
  onIconClick: () -> Unit,
) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .height(56.dp)
      .background(color = MaterialTheme.colors.primarySurface)
  ) {
    Image(
      imageVector = icon,
      colorFilter = ColorFilter
        .tint(MaterialTheme.colors.onPrimary),
      modifier = Modifier
        .clickable(onClick = onIconClick)
        .padding(16.dp)
        .align(Alignment.CenterVertically)
    )
    Text(
      text = title,
      color = MaterialTheme.colors.onPrimary,
      style = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp,
        letterSpacing = 0.15.sp
      ),
      modifier = Modifier
        .fillMaxWidth()
        .align(Alignment.CenterVertically)
        .padding(start = 16.dp, end = 16.dp)
    )
  }
}

@Preview
@Composable
private fun TopAppBarPreview() {
  JetNotesTheme {
    TopAppBar(
      title = "JetNotes",
      icon = Icons.Filled.List,
      onIconClick = {}
    )
  }
}