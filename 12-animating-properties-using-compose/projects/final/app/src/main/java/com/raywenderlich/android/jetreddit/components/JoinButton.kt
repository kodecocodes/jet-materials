package com.raywenderlich.android.jetreddit.components

import androidx.compose.animation.ColorPropKey
import androidx.compose.animation.DpPropKey
import androidx.compose.animation.core.*
import androidx.compose.animation.transition
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val buttonBackgroundColor = ColorPropKey(label = "Button Background Color")
private val buttonWidth = DpPropKey(label = "Button Width")
private val iconTintColor = ColorPropKey(label = "Icon Tint Color")
private val textMaxWidth = DpPropKey(label = "Text Max Width")

private val transitionDefinition =
  transitionDefinition<JoinButtonState> {
    state(JoinButtonState.IDLE) {
      this[buttonBackgroundColor] = Color.Blue
      this[buttonWidth] = 70.dp
      this[iconTintColor] = Color.White
      this[textMaxWidth] = 40.dp
    }

    state(JoinButtonState.PRESSED) {
      this[buttonBackgroundColor] = Color.White
      this[buttonWidth] = 32.dp
      this[iconTintColor] = Color.Blue
      this[textMaxWidth] = 0.dp
    }

    val duration = 600
    transition(
      fromState = JoinButtonState.IDLE,
      toState = JoinButtonState.PRESSED
    ) {
      buttonBackgroundColor using tween(duration)
      buttonWidth using tween(duration)
      iconTintColor using tween(duration)
      textMaxWidth using tween(duration)
    }

    transition(
      fromState = JoinButtonState.PRESSED,
      toState = JoinButtonState.IDLE
    ) {
      buttonBackgroundColor using tween(duration)
      buttonWidth using tween(duration)
      iconTintColor using tween(duration)
      textMaxWidth using tween(duration)
    }
  }

@Composable
fun JoinButton(onClick: (Boolean) -> Unit = {}) {
  var buttonState: JoinButtonState
    by remember { mutableStateOf(JoinButtonState.IDLE) }

  val transitionState = transition(
    definition = transitionDefinition,
    toState = buttonState
  )

  // Button shape
  val shape = RoundedCornerShape(corner = CornerSize(12.dp))

  // Button icon
  val iconAsset: ImageVector =
    if (buttonState == JoinButtonState.PRESSED)
      Icons.Default.Check
    else
      Icons.Default.Add

  Box(
    modifier = Modifier
      .clip(shape)
      .border(width = 1.dp, color = Color.Blue, shape = shape)
      .background(
        color = transitionState[buttonBackgroundColor]
      )
      .size(
        width = transitionState[buttonWidth],
        height = 24.dp
      )
      .clickable(onClick = {
        buttonState =
          if (buttonState == JoinButtonState.IDLE) {
            onClick.invoke(true)
            JoinButtonState.PRESSED
          } else {
            onClick.invoke(false)
            JoinButtonState.IDLE
          }
      }),
    contentAlignment = Alignment.Center
  ) {
    Row(
      verticalAlignment = Alignment.CenterVertically
    ) {
      Icon(
        imageVector = iconAsset,
        tint = transitionState[iconTintColor],
        modifier = Modifier.size(16.dp)
      )
      Text(
        text = "Join",
        color = Color.White,
        fontSize = 14.sp,
        maxLines = 1,
        modifier = Modifier.widthIn(
          min = 0.dp,
          max = transitionState[textMaxWidth]
        )
      )
    }
  }
}

enum class JoinButtonState {
  IDLE,
  PRESSED
}

@Preview
@Composable
fun JoinButtonPreview() {
  JoinButton(onClick = {})
}

