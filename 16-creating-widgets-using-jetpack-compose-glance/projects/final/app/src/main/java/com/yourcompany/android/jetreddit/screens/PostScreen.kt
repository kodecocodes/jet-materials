package com.yourcompany.android.jetreddit.screens

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yourcompany.android.jetreddit.R
import com.yourcompany.android.jetreddit.theme.JetRedditTheme

class PostActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    // Switch to AppTheme for displaying the activity
    setTheme(R.style.Theme_JetReddit)

    super.onCreate(savedInstanceState)

    setContent {
      JetRedditTheme {
        PostScreen(onBackClicked = { finish() })
      }
    }
  }
}

@Composable
fun PostScreen(onBackClicked: () -> Unit = {}) {
  val scaffoldState: ScaffoldState = rememberScaffoldState()
  Scaffold(
    scaffoldState = scaffoldState,
    topBar = { TopBar(onBackClicked) }
  ) { paddingValues ->
    Content(paddingValues = paddingValues)
  }
}

@Composable
private fun TopBar(onBackClicked: () -> Unit) {
  TopAppBar(
    title = {
      Text(
        text = "Post",
        color = colors.primaryVariant,
        modifier = Modifier.clearAndSetSemantics { }
      )
    },
    navigationIcon = {
      IconButton(onClick = onBackClicked) {
        Icon(
          Icons.Outlined.ArrowBack,
          tint = Color.LightGray,
          contentDescription = "Navigate up"
        )
      }
    },
    backgroundColor = colors.surface,
    elevation = 0.dp
  )
}

@Composable
private fun Content(paddingValues: PaddingValues) {
  Column(
    modifier = Modifier
      .padding(paddingValues)
      .verticalScroll(rememberScrollState())
  ) {
    TitleSection()
    SectionVerticalSpacer()
    AuthorSection()
    SectionVerticalSpacer()
    ContentSection()
    SectionVerticalSpacer()
    CommentsSection()
  }
}

@Composable
private fun TitleSection() {
  Text(
    text = "Check out this new book about Jetpack Compose from Kodeco!",
    color = colors.primaryVariant,
    fontSize = 18.sp,
    modifier = Modifier
      .padding(horizontal = 16.dp)
      .semantics { heading() }
  )
}

@Composable
private fun SectionVerticalSpacer() {
  Spacer(modifier = Modifier.height(16.dp))
}

@Composable
private fun CommentVerticalSpacer() {
  Spacer(modifier = Modifier.height(8.dp))
}

@Composable
private fun AuthorSection() {
  SectionDescriptor(text = "Author")
  Row(
    modifier = Modifier
      .padding(start = 16.dp)
      .semantics(mergeDescendants = true) { }
    ,
    verticalAlignment = Alignment.CenterVertically
  ) {
    Image(
      bitmap = ImageBitmap.imageResource(id = R.drawable.subreddit_placeholder),
      contentDescription = null,
      modifier = Modifier
        .size(40.dp)
        .clip(CircleShape)
    )
    Spacer(modifier = Modifier.width(8.dp))
    Column(modifier = Modifier.weight(1f)) {
      Text(
        "r/androiddev",
        fontWeight = FontWeight.Medium,
        color = colors.primaryVariant
      )
      Text(
        "Posted by u/johndoe - 1d",
        color = Color.Gray
      )
    }
  }
}

@Composable
private fun ContentSection() {
  SectionDescriptor(text = "Content")
  Text(
    text = "Check out this new book about Jetpack Compose from Kodeco! There is a new version with updated content and some new chapters!",
    color = colors.primaryVariant,
    fontSize = 14.sp,
    modifier = Modifier
      .padding(horizontal = 16.dp)
  )
}

@Composable
private fun CommentsSection() {
  SectionDescriptor(text = "Comments")
  CommentVerticalSpacer()
  DummyComment(count = 1, text = stringResource(id = R.string.dummy_coment_1))
  CommentVerticalSpacer()
  DummyComment(count = 2, text = stringResource(id = R.string.dummy_coment_2))
  CommentVerticalSpacer()
  DummyComment(count = 3, text = stringResource(id = R.string.dummy_coment_3))
  CommentVerticalSpacer()
  DummyComment(count = 4, text = stringResource(id = R.string.dummy_coment_4))
  CommentVerticalSpacer()
  DummyComment(count = 5, text = stringResource(id = R.string.dummy_coment_5))
  CommentVerticalSpacer()
  DummyComment(count = 6, text = stringResource(id = R.string.dummy_coment_6))
  CommentVerticalSpacer()
  DummyComment(count = 7, text = stringResource(id = R.string.dummy_coment_7))
  CommentVerticalSpacer()
  DummyComment(count = 8, text = stringResource(id = R.string.dummy_coment_8))
}

@Composable
private fun SectionDescriptor(text: String) {
  Text(
    text = text,
    color = Color.Gray,
    fontSize = 14.sp,
    modifier = Modifier
      .padding(horizontal = 16.dp)
      .semantics { heading() }
  )
}

@Composable
private fun DummyComment(
  count: Int,
  text: String
) {
  Text(
    text = "User $count",
    color = Color.Gray,
    fontSize = 12.sp,
    modifier = Modifier
      .padding(horizontal = 16.dp)
  )
  Text(
    text = text,
    color = Color.Black,
    fontSize = 12.sp,
    modifier = Modifier
      .padding(horizontal = 16.dp)
  )
}

@Preview
@Composable
fun PreviewPostScreen() {
  PostScreen()
}