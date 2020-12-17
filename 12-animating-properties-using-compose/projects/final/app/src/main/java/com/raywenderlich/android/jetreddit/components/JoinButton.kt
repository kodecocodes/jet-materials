/*
 * Copyright (c) 2020 Razeware LLC
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
package com.raywenderlich.android.jetreddit.components

import androidx.compose.runtime.Composable
import androidx.ui.tooling.preview.Preview

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

import androidx.compose.animation.animate

import androidx.compose.animation.ColorPropKey
import androidx.compose.animation.DpPropKey
import androidx.compose.animation.core.transitionDefinition
import androidx.compose.animation.core.tween

import androidx.compose.animation.transition

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.Text
import androidx.compose.ui.unit.sp

private val buttonBackgroundColor = ColorPropKey()
private val buttonWidth = DpPropKey()
private val iconTintColor = ColorPropKey()
private val textMaxWidth = DpPropKey()

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

      val duration: Int = 600
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

  // Button transition
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
fun PreviewJoinButton() {
  JoinButton(onClick = {})
}