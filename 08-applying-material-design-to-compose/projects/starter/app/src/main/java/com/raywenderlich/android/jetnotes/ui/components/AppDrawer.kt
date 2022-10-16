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

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.raywenderlich.android.jetnotes.routing.Screen
import com.raywenderlich.android.jetnotes.theme.JetNotesTheme
import com.raywenderlich.android.jetnotes.theme.JetNotesThemeSettings

@Composable
private fun AppDrawerHeader() {
  Row(modifier = Modifier.fillMaxWidth()) {
    Image(
      imageVector = Icons.Filled.Menu,
      contentDescription = "Drawer Header Icon",
      colorFilter = ColorFilter
        .tint(MaterialTheme.colors.onSurface),
      modifier = Modifier.padding(16.dp)
    )
    Text(
      text = "JetNotes",
      modifier = Modifier
        .align(alignment = Alignment.CenterVertically)
    )
  }
}

@Preview
@Composable
fun AppDrawerHeaderPreview() {
  JetNotesTheme {
    AppDrawerHeader()
  }
}

@Composable
private fun ScreenNavigationButton(
  icon: ImageVector,
  label: String,
  isSelected: Boolean,
  onClick: () -> Unit
) {
  val colors = MaterialTheme.colors

  // Define alphas for the image for two different states
  // of the button: selected/unselected
  val imageAlpha = if (isSelected) {
    1f
  } else {
    0.6f
  }

  // Define color for the text for two different states
  // of the button: selected/unselected
  val textColor = if (isSelected) {
    colors.primary
  } else {
    colors.onSurface.copy(alpha = 0.6f)
  }

  // Define color for the background for two different states
  // of the button: selected/unselected
  val backgroundColor = if (isSelected) {
    colors.primary.copy(alpha = 0.12f)
  } else {
    colors.surface
  }

  Surface( // 1
    modifier = Modifier
      .fillMaxWidth()
      .padding(start = 8.dp, end = 8.dp, top = 8.dp),
    color = backgroundColor,
    shape = MaterialTheme.shapes.small
  ) {
    Row( // 2
      horizontalArrangement = Arrangement.Start,
      verticalAlignment = Alignment.CenterVertically,
      modifier = Modifier
        .clickable(onClick = onClick)
        .fillMaxWidth()
        .padding(4.dp)
    ) {
      Image(
        imageVector = icon,
        contentDescription = "Screen Navigation Button",
        colorFilter = ColorFilter.tint(textColor),
        alpha = imageAlpha
      )
      Spacer(Modifier.width(16.dp)) // 3
      Text(
        text = label,
        style = MaterialTheme.typography.body2,
        color = textColor,
        modifier = Modifier.fillMaxWidth()
      )
    }
  }
}

@Preview
@Composable
fun ScreenNavigationButtonPreview() {
  JetNotesTheme {
    ScreenNavigationButton(
      icon = Icons.Filled.Home,
      label = "Notes",
      isSelected = true,
      onClick = { }
    )
  }
}

@Composable
private fun LightDarkThemeItem() {
  Row(
    Modifier
      .padding(8.dp)
  ) {
    Text(
      text = "Turn on dark theme",
      style = MaterialTheme.typography.body2,
      color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f),
      modifier = Modifier
        .weight(1f)
        .padding(start = 8.dp, top = 8.dp, end = 8.dp, bottom = 8.dp)
        .align(alignment = Alignment.CenterVertically)
    )
    Switch(
      checked = JetNotesThemeSettings.isDarkThemeEnabled,
      onCheckedChange = { JetNotesThemeSettings.isDarkThemeEnabled = it },
      modifier = Modifier
        .padding(start = 8.dp, end = 8.dp)
        .align(alignment = Alignment.CenterVertically)
    )
  }
}

@Preview
@Composable
fun LightDarkThemeItemPreview() {
  JetNotesTheme {
    LightDarkThemeItem()
  }
}

@Composable
fun AppDrawer(
  currentScreen: Screen,
  onScreenSelected: (Screen) -> Unit
) {
  Column(modifier = Modifier.fillMaxSize()) {
    AppDrawerHeader()

    Divider(color = MaterialTheme.colors.onSurface.copy(alpha = .2f))

    ScreenNavigationButton(
      icon = Icons.Filled.Home,
      label = "Notes",
      isSelected = currentScreen == Screen.Notes,
      onClick = {
        onScreenSelected.invoke(Screen.Notes)
      }
    )
    ScreenNavigationButton(
      icon = Icons.Filled.Delete,
      label = "Trash",
      isSelected = currentScreen == Screen.Trash,
      onClick = {
        onScreenSelected.invoke(Screen.Trash)
      }
    )
    LightDarkThemeItem()
  }
}

@Preview
@Composable
fun AppDrawerPreview() {
  JetNotesTheme {
    AppDrawer(Screen.Notes, {})
  }
}