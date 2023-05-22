/*
 * Copyright (c) 2022 Kodeco Inc
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

package com.yourcompany.android.jetpackcompose.screens

import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import com.yourcompany.android.jetpackcompose.R
import com.yourcompany.android.jetpackcompose.router.BackButtonHandler
import com.yourcompany.android.jetpackcompose.router.JetFundamentalsRouter
import com.yourcompany.android.jetpackcompose.router.Screen


@Composable
fun AlertDialogScreen() {

  MyAlertDialog()

  BackButtonHandler {
    JetFundamentalsRouter.navigateTo(Screen.Navigation)
  }
}

@Composable
fun MyAlertDialog() {
  val shouldShowDialog = remember { mutableStateOf(true) }

  if (shouldShowDialog.value) {
    AlertDialog(
      onDismissRequest = {
        shouldShowDialog.value = false
        JetFundamentalsRouter.navigateTo(Screen.Navigation)
      },
      title = { Text(text = stringResource(id = R.string.alert_dialog_title)) },
      text = { Text(text = stringResource(id = R.string.alert_dialog_text)) },
      confirmButton = {
        Button(
          colors = ButtonDefaults.buttonColors(
            backgroundColor = colorResource(id = R.color.colorPrimary)
          ),
          onClick = {
            shouldShowDialog.value = false
            JetFundamentalsRouter.navigateTo(Screen.Navigation)
          }) {
          Text(
            text = stringResource(id = R.string.confirm),
            color = Color.White
          )
        }
      }
    )
  }
}