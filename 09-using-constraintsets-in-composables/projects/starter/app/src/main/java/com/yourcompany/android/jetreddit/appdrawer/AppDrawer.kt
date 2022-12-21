/*
 * Copyright (c) 2022 Kodeco Inc.
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
package com.yourcompany.android.jetreddit.appdrawer

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yourcompany.android.jetreddit.R
import com.yourcompany.android.jetreddit.routing.Screen
import com.yourcompany.android.jetreddit.theme.JetRedditThemeSettings

/**
 * Represents root composable for the app drawer used in screens
 */
@Composable
fun AppDrawer(
  modifier: Modifier = Modifier,
  onScreenSelected: (Screen) -> Unit
) {
  Column(
    modifier = modifier
      .fillMaxSize()
      .background(color = MaterialTheme.colors.surface)
  ) {
    AppDrawerHeader()

    AppDrawerBody(onScreenSelected)

    AppDrawerFooter(modifier)
  }
}

/**
 * Represents app drawer header with the icon and the app name
 */
@Composable
private fun AppDrawerHeader() {
  //TODO add your code here
}

@Composable
fun ProfileInfo() {
  //TODO add your code here
}

@Composable
private fun ProfileInfoItem(
  iconAsset: ImageVector,
  amountResourceId: Int,
  textResourceId: Int,
  modifier: Modifier
) {
  //TODO add your code here
}

/**
 * Represents app drawer actions:
 * * screen navigation
 * * app light/dark mode
 */
@Composable
private fun AppDrawerBody(
  onScreenSelected: (Screen) -> Unit
) {
  //TODO add your code here
}

/**
 * Represents component in the app drawer that the user can use to change the screen
 */
@Composable
private fun ScreenNavigationButton(
  icon: ImageVector,
  label: String,
  onClickAction: () -> Unit,
  modifier: Modifier = Modifier
) {
  val colors = MaterialTheme.colors

  val surfaceModifier = modifier
    .padding(start = 8.dp, top = 8.dp, end = 8.dp)
    .fillMaxWidth()

  Surface(
    modifier = surfaceModifier,
    color = colors.surface,
    shape = MaterialTheme.shapes.small
  ) {
    TextButton(
      onClick = onClickAction,
      modifier = Modifier.fillMaxWidth()
    ) {
      Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
      ) {
        Image(
          imageVector = icon,
          colorFilter = ColorFilter.tint(Color.Gray),
          contentDescription = label
        )
        Spacer(Modifier.width(16.dp))
        Text(
          fontSize = 10.sp,
          text = label,
          style = MaterialTheme.typography.body2,
          color = colors.primaryVariant
        )
      }
    }
  }
}

/**
 * Represents setting component in the app drawer
 */
@Composable
private fun AppDrawerFooter(modifier: Modifier = Modifier) {
  //TODO add your code here
}

private fun changeTheme() {
  JetRedditThemeSettings.isInDarkTheme.value = JetRedditThemeSettings.isInDarkTheme.value.not()
}

@Preview
@Composable
private fun ProfileInfoItemPreview() {
  ProfileInfoItem(
    Icons.Filled.ShoppingCart,
    R.string.default_reddit_age_amount,
    R.string.reddit_age,
    Modifier
  )
}
