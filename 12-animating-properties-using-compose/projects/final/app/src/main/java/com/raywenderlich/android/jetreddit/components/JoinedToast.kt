package com.raywenderlich.android.jetreddit.components

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.raywenderlich.android.jetreddit.R

@ExperimentalAnimationApi
@Composable
fun JoinedToast(visible: Boolean) {
  AnimatedVisibility(
    visible = visible,
    enter = slideInVertically(initialOffsetY = { +40 }) +
        fadeIn(),
    exit = slideOutVertically() + fadeOut()
  ) {
    ToastContent()
  }
}

@Composable
private fun ToastContent() {
  val shape = RoundedCornerShape(4.dp)
  Box(
    modifier = Modifier
      .clip(shape)
      .background(Color.White)
      .border(1.dp, Color.Black, shape)
      .height(40.dp)
      .padding(horizontal = 8.dp),
    contentAlignment = Alignment.Center
  ) {
    Row(verticalAlignment = Alignment.CenterVertically) {
      Icon(
        imageVector = vectorResource(
          id = R.drawable.ic_planet
        )
      )
      Spacer(modifier = Modifier.width(8.dp))
      Text(text = "You have joined this community!")
    }
  }
}

@ExperimentalAnimationApi
@Preview
@Composable
fun JoinedToastPreview() {
  JoinedToast(visible = true)
}