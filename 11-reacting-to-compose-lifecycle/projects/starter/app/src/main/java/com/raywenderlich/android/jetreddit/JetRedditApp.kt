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
package com.raywenderlich.android.jetreddit


import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.raywenderlich.android.jetreddit.appdrawer.AppDrawer
import com.raywenderlich.android.jetreddit.routing.JetRedditRouter
import com.raywenderlich.android.jetreddit.routing.Screen
import com.raywenderlich.android.jetreddit.screens.*
import com.raywenderlich.android.jetreddit.screens.MyProfileScreen
import com.raywenderlich.android.jetreddit.theme.JetRedditTheme
import com.raywenderlich.android.jetreddit.viewmodel.MainViewModel

@Composable
fun JetRedditApp(viewModel: MainViewModel) {
  JetRedditTheme {
    AppContent(viewModel)
  }
}

@Composable
private fun AppContent(viewModel: MainViewModel) {
  val scaffoldState: ScaffoldState = rememberScaffoldState()
  Crossfade(current = JetRedditRouter.currentScreen) { screenState: MutableState<Screen> ->

    Scaffold(
        topBar = getTopBar(screenState.value, scaffoldState),
        drawerContent = {
          AppDrawer(
              closeDrawerAction = { scaffoldState.drawerState.close() }
          )
        },
        scaffoldState = scaffoldState,
        bottomBar = {
          BottomNavigationComponent(screenState = screenState)
        },
        bodyContent = {
          MainScreenContainer(
              modifier = Modifier.padding(bottom = 56.dp),
              screenState = screenState,
              viewModel = viewModel
          )
        }
    )
  }
}

fun getTopBar(screenState: Screen, scaffoldState: ScaffoldState): @Composable (() -> Unit) {
  if (screenState == Screen.MyProfile || screenState == Screen.ChooseCommunity) {
    return {}
  } else {
    return { TopAppBar(scaffoldState = scaffoldState) }
  }
}

/**
 * Represents top app bar on the screen
 */
@Composable
fun TopAppBar(scaffoldState: ScaffoldState) {

  val colors = MaterialTheme.colors

  TopAppBar(
      title = {
        Text(
            text = stringResource(JetRedditRouter.currentScreen.value.titleResId),
            color = colors.primaryVariant
        )
      },
      backgroundColor = colors.surface,
      navigationIcon = {
        IconButton(onClick = {
          scaffoldState.drawerState.open()
        }) {
          Icon(
              Icons.Filled.AccountCircle,
              tint = Color.LightGray
          )
        }
      }
  )
}

@Composable
private fun MainScreenContainer(modifier: Modifier = Modifier, screenState: MutableState<Screen>, viewModel: MainViewModel) {
  Surface(
      modifier = modifier,
      color = MaterialTheme.colors.background
  ) {
    when (screenState.value) {
      Screen.Home -> HomeScreen(viewModel)
      Screen.Subscriptions -> SubredditsScreen()
      Screen.NewPost -> AddScreen(viewModel)
      Screen.MyProfile -> MyProfileScreen(viewModel)
      Screen.ChooseCommunity -> ChooseCommunityScreen(viewModel)
    }
  }
}

@Composable
private fun BottomNavigationComponent(
    modifier: Modifier = Modifier,
    screenState: MutableState<Screen>
) {
  var selectedItem by remember { mutableStateOf(0) }

  val items = listOf(
      NavigationItem(0, R.drawable.ic_baseline_home_24, Screen.Home),
      NavigationItem(1, R.drawable.ic_baseline_format_list_bulleted_24, Screen.Subscriptions),
      NavigationItem(2, R.drawable.ic_baseline_add_24, Screen.NewPost),
  )
  BottomNavigation(modifier = modifier) {
    items.forEach {
      BottomNavigationItem(
          icon = { Icon(vectorResource(id = it.vectorResourceId)) },
          selected = selectedItem == it.index,
          onClick = {
            selectedItem = it.index
            screenState.value = it.screen
          }
      )
    }
  }
}

private data class NavigationItem(val index: Int, val vectorResourceId: Int, val screen: Screen)