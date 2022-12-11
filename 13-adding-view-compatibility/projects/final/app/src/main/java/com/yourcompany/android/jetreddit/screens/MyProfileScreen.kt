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
package com.yourcompany.android.jetreddit.screens

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.yourcompany.android.jetreddit.R
import com.yourcompany.android.jetreddit.appdrawer.ProfileInfo
import com.yourcompany.android.jetreddit.components.PostAction
import com.yourcompany.android.jetreddit.domain.model.PostModel
import com.yourcompany.android.jetreddit.routing.MyProfileRouter
import com.yourcompany.android.jetreddit.routing.MyProfileScreenType
import com.yourcompany.android.jetreddit.viewmodel.MainViewModel

private val tabNames = listOf(R.string.posts, R.string.about)

@Composable
fun MyProfileScreen(
  viewModel: MainViewModel,
  modifier: Modifier = Modifier,
  onBackSelected: () -> Unit
) {
  ConstraintLayout(modifier = modifier.fillMaxSize()) {
    val (topAppBar, tabs, bodyContent) = createRefs()

    val colors = MaterialTheme.colors

    TopAppBar(
      title = {
        Text(
          fontSize = 12.sp,
          text = stringResource(R.string.default_username),
          color = colors.primaryVariant
        )
      },
      navigationIcon = {
        IconButton(
          onClick = { onBackSelected.invoke() }
        ) {
          Icon(
            imageVector = Icons.Default.ArrowBack,
            tint = colors.primaryVariant,
            contentDescription = stringResource(id = R.string.back)
          )
        }
      },
      backgroundColor = colors.primary,
      elevation = 0.dp,
      modifier = modifier
        .constrainAs(topAppBar) {
          top.linkTo(parent.top)
          start.linkTo(parent.start)
          end.linkTo(parent.end)
        }
        .height(48.dp)
        .background(Color.Blue)
    )

    MyProfileTabs(
      modifier = modifier.constrainAs(tabs) {
        top.linkTo(topAppBar.bottom)
        start.linkTo(parent.start)
        end.linkTo(parent.end)
      }
    )

    Surface(
      modifier = modifier
        .constrainAs(bodyContent) {
          top.linkTo(tabs.bottom)
          start.linkTo(parent.start)
          end.linkTo(parent.end)
        }
        .padding(bottom = 68.dp)
    ) {

      Crossfade(targetState = MyProfileRouter.currentScreen) { screen ->
        when (screen.value) {
          MyProfileScreenType.Posts -> MyProfilePosts(modifier, viewModel)
          MyProfileScreenType.About -> MyProfileAbout()
        }
      }
    }
  }
}

@Composable
fun MyProfileTabs(modifier: Modifier = Modifier) {
  var selectedIndex by remember { mutableStateOf(0) }
  TabRow(
    selectedTabIndex = selectedIndex,
    backgroundColor = MaterialTheme.colors.primary,
    modifier = modifier
  ) {
    tabNames.forEachIndexed { index, nameResource ->
      Tab(
        selected = index == selectedIndex,
        onClick = {
          selectedIndex = index
          changeScreen(index)
        }
      ) {
        Text(
          color = MaterialTheme.colors.primaryVariant,
          fontSize = 12.sp,
          text = stringResource(nameResource),
          modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
        )
      }
    }
  }
}

private fun changeScreen(index: Int) {
  return when (index) {
    0 -> MyProfileRouter.navigateTo(MyProfileScreenType.Posts)
    else -> MyProfileRouter.navigateTo(MyProfileScreenType.About)
  }
}

@Composable
fun MyProfilePosts(modifier: Modifier, viewModel: MainViewModel) {

  val posts: List<PostModel> by viewModel.myPosts.observeAsState(listOf())

  LazyColumn(
    modifier = modifier.background(color = MaterialTheme.colors.secondary)
  ) {
    items(posts) { MyProfilePost(modifier, it) }
  }
}

@Composable
fun MyProfilePost(modifier: Modifier, post: PostModel) {
  Card(shape = MaterialTheme.shapes.large) {

    ConstraintLayout(
      modifier = modifier.fillMaxSize()
    ) {

      val (redditIcon, subredditName, actionsBar, title, description, settingIcon) = createRefs()
      val postModifier = Modifier
      val colors = MaterialTheme.colors

      Image(
        imageVector = Icons.Default.Star,
        contentDescription = stringResource(id = R.string.my_profile),
        modifier = postModifier
          .size(20.dp)
          .constrainAs(redditIcon) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
          }
          .padding(start = 8.dp, top = 8.dp)
      )

      Image(
        imageVector = ImageVector.vectorResource(id = R.drawable.ic_baseline_more_vert_24),
        contentDescription = stringResource(id = R.string.more_actions),
        modifier = postModifier
          .size(20.dp)
          .constrainAs(settingIcon) {
            top.linkTo(parent.top)
            end.linkTo(parent.end)
          }
          .padding(end = 8.dp, top = 8.dp)
      )

      Text(
        text = "${post.username} • ${post.postedTime}",
        fontSize = 8.sp,
        modifier = postModifier
          .constrainAs(subredditName) {
            top.linkTo(redditIcon.top)
            bottom.linkTo(redditIcon.bottom)
            start.linkTo(redditIcon.end)
          }
          .padding(start = 2.dp, top = 8.dp)
      )

      Text(
        text = post.title,
        color = colors.primaryVariant,
        fontSize = 12.sp,
        modifier = postModifier
          .constrainAs(title) {
            top.linkTo(redditIcon.bottom)
            start.linkTo(redditIcon.start)
          }
          .padding(start = 8.dp, top = 8.dp)
      )

      Text(
        text = post.text,
        color = Color.DarkGray,
        fontSize = 10.sp,
        modifier = postModifier
          .constrainAs(description) {
            top.linkTo(title.bottom)
            start.linkTo(redditIcon.start)
          }
          .padding(start = 8.dp, top = 8.dp)
      )

      Row(
        modifier = postModifier
          .fillMaxWidth()
          .constrainAs(actionsBar) {
            top.linkTo(description.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
          }
          .padding(
            top = 8.dp,
            bottom = 8.dp,
            end = 16.dp,
            start = 16.dp
          ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        PostAction(
          vectorResourceId = R.drawable.ic_baseline_arrow_upward_24,
          text = post.likes,
          onClickAction = {}
        )
        PostAction(
          vectorResourceId = R.drawable.ic_baseline_comment_24,
          text = post.comments,
          onClickAction = {}
        )
        PostAction(
          vectorResourceId = R.drawable.ic_baseline_share_24,
          text = stringResource(R.string.share),
          onClickAction = {}
        )
      }
    }
  }

  Spacer(modifier = Modifier.height(6.dp))
}

@Composable
fun MyProfileAbout() {
  Column {
    ProfileInfo()

    Spacer(modifier = Modifier.height(8.dp))

    BackgroundText(stringResource(R.string.trophies))

    val trophies = listOf(
      R.string.verified_email,
      R.string.gold_medal,
      R.string.top_comment
    )
    LazyColumn {
      items(trophies) { Trophy(text = stringResource(it)) }
    }
  }
}

@Composable
fun ColumnScope.BackgroundText(text: String) {
  Text(
    fontWeight = FontWeight.Medium,
    text = text,
    fontSize = 10.sp,
    color = Color.DarkGray,
    modifier = Modifier
      .background(color = MaterialTheme.colors.secondary)
      .padding(start = 16.dp, top = 4.dp, bottom = 4.dp)
      .fillMaxWidth()
      .align(Alignment.Start)

  )
}

@Composable
fun Trophy(text: String, modifier: Modifier = Modifier) {
  Spacer(modifier = modifier.height(16.dp))
  Row(verticalAlignment = Alignment.CenterVertically) {
    Spacer(modifier = modifier.width(16.dp))
    Image(
      bitmap = ImageBitmap.imageResource(id = R.drawable.trophy),
      contentDescription = stringResource(id = R.string.trophies),
      contentScale = ContentScale.Crop,
      modifier = modifier.size(24.dp)
    )
    Spacer(modifier = modifier.width(16.dp))
    Text(
      text = text, fontSize = 12.sp,
      color = MaterialTheme.colors.primaryVariant,
      textAlign = TextAlign.Center,
      fontWeight = FontWeight.Medium
    )
  }
}