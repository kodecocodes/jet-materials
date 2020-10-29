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
package com.raywenderlich.android.jetreddit.appdrawer

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.VectorAsset
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.raywenderlich.android.jetreddit.R
import com.raywenderlich.android.jetreddit.theme.JetRedditThemeSettings

/**
 * Represents root composable for the app drawer used in screens
 */
@Composable
fun AppDrawer(closeDrawerAction: () -> Unit, modifier: Modifier = Modifier) {
  Column(
      modifier = modifier
          .fillMaxSize()
          .background(color = MaterialTheme.colors.surface)
  ) {
    AppDrawerHeader()
    Divider(
        color = MaterialTheme.colors.onSurface.copy(alpha = .2f),
        modifier = modifier.padding(
            start = 16.dp,
            end = 16.dp,
            top = 16.dp
        )
    )
    AppDrawerBody(closeDrawerAction)

    AppDrawerFooter(modifier)
  }
}

/**
 * Represents app drawer header with the icon and the app name
 */
@Composable
private fun AppDrawerHeader() {
  Column(
      modifier = Modifier.fillMaxWidth(),
      horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Image(
        asset = Icons.Filled.AccountCircle,
        colorFilter = ColorFilter.tint(Color.LightGray),
        modifier = Modifier
            .padding(16.dp)
            .size(50.dp),
        contentScale = ContentScale.Fit,
        alignment = Alignment.Center
    )

    Text(
        text = stringResource(R.string.default_username),
        color = MaterialTheme.colors.primaryVariant
    )

    ProfileInfo()
  }
}

@Composable
fun ProfileInfo() {
  ConstraintLayout(
      modifier = Modifier
          .fillMaxWidth()
          .padding(top = 16.dp)
  ) {

    val (karmaIcon, karmaAmount, karmaText, divider, ageIcon, ageAmount, ageText) = createRefs()
    val colors = MaterialTheme.colors

    Icon(
        asset = Icons.Filled.Star,
        tint = Color.Blue,
        modifier = Modifier
            .constrainAs(karmaIcon) {
              start.linkTo(parent.start)
              top.linkTo(parent.top)
              bottom.linkTo(parent.bottom)
            }.padding(start = 16.dp)
    )

    Text(
        text = stringResource(R.string.default_karma_amount),
        color = colors.primaryVariant,
        fontSize = 10.sp,
        modifier = Modifier
            .padding(start = 8.dp)
            .constrainAs(karmaAmount) {
              top.linkTo(karmaIcon.top)
              start.linkTo(karmaIcon.end)
              bottom.linkTo(karmaText.top)
            }
    )

    Text(
        text = stringResource(R.string.karma),
        color = Color.Gray,
        fontSize = 10.sp,
        modifier = Modifier
            .padding(start = 8.dp)
            .constrainAs(karmaText) {
              top.linkTo(karmaAmount.bottom)
              start.linkTo(karmaIcon.end)
              bottom.linkTo(karmaIcon.bottom)
            }
    )

    Divider(
        modifier = Modifier
            .width(1.dp)
            .height(28.dp)
            .constrainAs(divider) {
              top.linkTo(karmaAmount.top)
              bottom.linkTo(karmaText.bottom)
              start.linkTo(parent.start)
              end.linkTo(parent.end)
            },
        color = colors.onSurface.copy(alpha = .2f)
    )

    Icon(
        asset = Icons.Filled.ShoppingCart,
        tint = Color.Blue,
        modifier = Modifier
            .padding(start = 16.dp)
            .constrainAs(ageIcon) {
              start.linkTo(divider.start)
              top.linkTo(parent.top)
              bottom.linkTo(parent.bottom)
            }
    )

    Text(
        text = stringResource(R.string.default_reddit_age),
        color = colors.primaryVariant,
        fontSize = 10.sp,
        modifier = Modifier
            .padding(start = 8.dp)
            .constrainAs(ageAmount) {
              top.linkTo(ageIcon.top)
              start.linkTo(ageIcon.end)
              bottom.linkTo(ageText.top)
            }
    )

    Text(
        text = stringResource(R.string.reddit_age),
        color = Color.Gray,
        fontSize = 10.sp,
        modifier = Modifier
            .padding(start = 8.dp)
            .constrainAs(ageText) {
              top.linkTo(ageAmount.bottom)
              start.linkTo(ageIcon.end)
              bottom.linkTo(ageIcon.bottom)
            }
    )
  }
}

/**
 * Represents app drawer actions:
 * * screen navigation
 * * app light/dark mode
 */
@Composable
private fun AppDrawerBody(closeDrawerAction: () -> Unit) {
  ScreenNavigationButton(
      icon = Icons.Filled.AccountBox,
      label = stringResource(R.string.my_profile),
      onClickAction = {
        closeDrawerAction()
      }
  )

  ScreenNavigationButton(
      icon = Icons.Filled.Home,
      label = stringResource(R.string.saved),
      onClickAction = {
        closeDrawerAction()
      }
  )
}

/**
 * Represents component in the app drawer that the user can use to change the screen
 */
@Composable
private fun ScreenNavigationButton(
    icon: VectorAsset,
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
            asset = icon,
            colorFilter = ColorFilter.tint(Color.Gray)
        )
        Spacer(Modifier.preferredWidth(16.dp))
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
  ConstraintLayout(
      modifier = modifier
          .fillMaxSize()
          .padding(
              start = 16.dp,
              bottom = 16.dp,
              end = 16.dp
          )
  ) {

    val colors = MaterialTheme.colors
    val (settingsImage, settingsText, darkModeButton) = createRefs()

    Image(
        modifier = modifier.constrainAs(settingsImage) {
          start.linkTo(parent.start)
          bottom.linkTo(parent.bottom)
        },
        asset = Icons.Default.Settings,
        colorFilter = ColorFilter.tint(colors.primaryVariant)
    )

    Text(
        fontSize = 10.sp,
        text = stringResource(R.string.settings),
        style = MaterialTheme.typography.body2,
        color = colors.primaryVariant,
        modifier = modifier
            .padding(start = 16.dp)
            .constrainAs(settingsText) {
              start.linkTo(settingsImage.end)
              bottom.linkTo(settingsImage.bottom)
              top.linkTo(settingsImage.top)
            }
    )

    Image(
        asset = vectorResource(id = R.drawable.ic_baseline_brightness_3_24),
        modifier = modifier
            .size(24.dp)
            .clickable(onClick = { changeTheme() })
            .constrainAs(darkModeButton) {
              end.linkTo(parent.end)
              bottom.linkTo(settingsImage.bottom)
              top.linkTo(settingsImage.top)
            },
        colorFilter = ColorFilter.tint(colors.primaryVariant)
    )
  }
}

private fun changeTheme() {
  JetRedditThemeSettings.isInDarkTheme.value = JetRedditThemeSettings.isInDarkTheme.value.not()
}
