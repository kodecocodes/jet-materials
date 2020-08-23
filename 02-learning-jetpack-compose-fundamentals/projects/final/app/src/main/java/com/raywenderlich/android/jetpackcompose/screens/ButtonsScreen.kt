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

package com.raywenderlich.android.jetpackcompose.screens

import androidx.compose.Composable
import androidx.compose.getValue
import androidx.compose.setValue
import androidx.compose.state
import androidx.ui.core.Alignment
import androidx.ui.core.Modifier
import androidx.ui.foundation.Border
import androidx.ui.foundation.Icon
import androidx.ui.foundation.Text
import androidx.ui.graphics.Color
import androidx.ui.layout.Arrangement
import androidx.ui.layout.Column
import androidx.ui.layout.fillMaxSize
import androidx.ui.material.Button
import androidx.ui.material.FloatingActionButton
import androidx.ui.material.RadioGroup
import androidx.ui.material.icons.Icons
import androidx.ui.material.icons.filled.Favorite
import androidx.ui.res.colorResource
import androidx.ui.res.stringResource
import androidx.ui.unit.dp
import com.raywenderlich.android.jetpackcompose.R
import com.raywenderlich.android.jetpackcompose.router.BackButtonHandler
import com.raywenderlich.android.jetpackcompose.router.JetFundamentalsRouter
import com.raywenderlich.android.jetpackcompose.router.Screen

@Composable
fun ExploreButtonsScreen() {
  Column(modifier = Modifier.fillMaxSize(),
      horizontalGravity = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center) {

    MyButton()
    MyRadioGroup()
    MyFloatingActionButton()

    BackButtonHandler {
      JetFundamentalsRouter.navigateTo(Screen.Navigation)
    }
  }
}

@Composable
fun MyButton() {
  Button(
      onClick = {},
      backgroundColor = colorResource(id = R.color.colorPrimary),
      border = Border(
          1.dp,
          color = colorResource(id = R.color.colorPrimaryDark)
      )
  ) {
    Text(
        text = stringResource(id = R.string.button_text),
        color = Color.White
    )
  }
}

@Composable
fun MyRadioGroup() {
  val options = listOf(
      stringResource(id = R.string.first),
      stringResource(id = R.string.second),
      stringResource(id = R.string.third)
  )

  var radioState by state { options.first() }

  RadioGroup(
      options = options,
      radioColor = colorResource(id = R.color.colorPrimary),
      selectedOption = radioState,
      onSelectedChange = { selectedOption ->
        radioState = selectedOption
      }
  )
}

@Composable
fun MyFloatingActionButton() {
  FloatingActionButton(
      onClick = {},
      backgroundColor = colorResource(id = R.color.colorPrimary),
      contentColor = Color.White,
      icon = { Icon(Icons.Filled.Favorite) }
  )
}
