package com.raywenderlich.android.jetreddit.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredHeight
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.raywenderlich.android.jetreddit.R
import com.raywenderlich.android.jetreddit.routing.JetRedditRouter
import com.raywenderlich.android.jetreddit.viewmodel.MainViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.delay

private const val SEARCH_DELAY_MILLIS = 300L

private val defaultCommunities = listOf("raywenderlich", "androiddev", "puppies")

@Composable
fun ChooseCommunityScreen(viewModel: MainViewModel, modifier: Modifier = Modifier) {

  val scope = rememberCoroutineScope()
  var currentJob by remember { mutableStateOf<Job?>(null) }

  val communities: List<String> by viewModel.subreddits.observeAsState(emptyList())

  var searchedText by remember { mutableStateOf("") }

  onActive {
    viewModel.searchCommunities(searchedText)
  }

  Column {
    ChooseCommunityTopBar()

    TextField(
        value = searchedText,
        onValueChange = {
          searchedText = it
          currentJob?.cancel()
          currentJob = scope.async {
            delay(SEARCH_DELAY_MILLIS)
            viewModel.searchCommunities(searchedText)
          }
        },
        leadingIcon = { Icon(Icons.Default.Search) },
        label = { Text(stringResource(R.string.search)) },
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        backgroundColor = MaterialTheme.colors.surface,
        activeColor = MaterialTheme.colors.onSurface
    )
    SearchedCommunities(communities, viewModel, modifier)
  }
}

@Composable
fun SearchedCommunities(communities: List<String>, viewModel: MainViewModel?, modifier: Modifier = Modifier) {
  communities.forEach {
    Community(
        text = it,
        modifier = modifier,
        onCommunityClicked = {
          viewModel?.selectedCommunity?.postValue(it)
          JetRedditRouter.goBack()
        }
    )
  }
}

@Composable
fun ChooseCommunityTopBar(modifier: Modifier = Modifier) {

  val colors = MaterialTheme.colors

  TopAppBar(
      title = {
        Text(
            fontSize = 16.sp,
            text = stringResource(R.string.choose_community),
            color = colors.primaryVariant
        )
      },
      navigationIcon = {
        IconButton(
            onClick = { JetRedditRouter.goBack() }
        ) {
          Icon(
              imageVector = Icons.Default.Close,
              tint = colors.primaryVariant
          )
        }
      },
      backgroundColor = colors.primary,
      elevation = 0.dp,
      modifier = modifier
          .preferredHeight(48.dp)
          .background(Color.Blue)
  )
}

@Preview
@Composable
fun SearchedCommunitiesPreview() {
  Column {
    SearchedCommunities(defaultCommunities, null, Modifier)
  }
}
