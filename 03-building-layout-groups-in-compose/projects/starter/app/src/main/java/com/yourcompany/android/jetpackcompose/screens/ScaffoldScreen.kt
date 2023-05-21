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

import android.annotation.SuppressLint
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.yourcompany.android.jetpackcompose.R
import com.yourcompany.android.jetpackcompose.router.BackButtonHandler
import com.yourcompany.android.jetpackcompose.router.JetFundamentalsRouter
import com.yourcompany.android.jetpackcompose.router.Screen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun ScaffoldScreen() {
  MyScaffold()

  BackButtonHandler {
    JetFundamentalsRouter.navigateTo(Screen.Navigation)
  }
}

@Preview(showBackground=true)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MyScaffold() {
  val scaffoldState: ScaffoldState = rememberScaffoldState()
  val scope: CoroutineScope = rememberCoroutineScope()

  Scaffold(
    scaffoldState = scaffoldState,
    contentColor = colorResource(id = R.color.colorPrimary),
    content = { MyRow() },
    topBar = { MyTopAppBar(scaffoldState = scaffoldState, scope = scope) },
    bottomBar = { MyBottomAppBar() },
    drawerContent = { MyColumn() }
  )
}

@Composable
fun MyTopAppBar(scaffoldState: ScaffoldState, scope: CoroutineScope) {
  val drawerState = scaffoldState.drawerState

  TopAppBar(
    title = {
      Text(
        text = stringResource(id = R.string.app_name),
        color = Color.White
      )
    },
    backgroundColor = colorResource(id = R.color.colorPrimary),
    navigationIcon = {
      IconButton(onClick = {
        scope.launch {
          if (drawerState.isClosed) {
            drawerState.open()
          } else {
            drawerState.close()
          }
        }
      }) {
        Icon(
          Icons.Default.ArrowBack,
          contentDescription = stringResource(id = R.string.menu),
          tint = Color.White
        )
      }
    }
  )
}

@Composable
fun MyBottomAppBar() {
  BottomAppBar(
    backgroundColor = colorResource(id = R.color.colorAccent),
    content = { MyRow() }
  )
}

