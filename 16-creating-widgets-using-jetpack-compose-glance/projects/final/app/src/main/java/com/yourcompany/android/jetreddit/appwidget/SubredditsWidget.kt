package com.yourcompany.android.jetreddit.appwidget

import android.appwidget.AppWidgetManager
import android.content.Context
import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.glance.*
import androidx.glance.action.ActionParameters
import androidx.glance.action.actionParametersOf
import androidx.glance.appwidget.*
import androidx.glance.appwidget.action.ActionCallback
import androidx.glance.appwidget.action.ToggleableStateKey
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.appwidget.lazy.LazyColumn
import androidx.glance.appwidget.lazy.items
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.glance.layout.*
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.FixedColorProvider
import com.yourcompany.android.jetreddit.R
import com.yourcompany.android.jetreddit.dependencyinjection.dataStore
import com.yourcompany.android.jetreddit.screens.communities
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.map

private val toggledSubredditIdKey = ActionParameters.Key<String>("ToggledSubredditIdKey")

class SubredditsWidget : GlanceAppWidget() {

  @Composable
  override fun Content() {
    Column(
      modifier = GlanceModifier
        .fillMaxSize()
        .padding(16.dp)
        .appWidgetBackground()
        .background(Color.White)
        .cornerRadius(16.dp)
    ) {
      WidgetTitle()
      ScrollableSubredditsList()
    }
  }

  @Preview
  @Composable
  fun PreviewContent() {
    Content()
  }
}

@Composable
fun WidgetTitle() {
  Text(
    text = "Subreddits",
    modifier = GlanceModifier
      .fillMaxWidth(),
    style = TextStyle(
      fontWeight = FontWeight.Bold,
      fontSize = 18.sp,
      color = FixedColorProvider(Color.Black)
    ),
  )
}

@Composable
fun ScrollableSubredditsList() {
  LazyColumn {
    items(communities) { communityId ->
      Subreddit(id = communityId)
    }
  }
}

@Composable
fun Subreddit(@StringRes id: Int) {
  val prefs = currentState<Preferences>()
  val checked = prefs[booleanPreferencesKey(id.toString())] ?: false

  Row(
    modifier = GlanceModifier
      .padding(top = 16.dp)
      .fillMaxWidth(),
    verticalAlignment = Alignment.CenterVertically
  ) {

    Image(
      provider = ImageProvider(R.drawable.subreddit_placeholder),
      contentDescription = null,
      modifier = GlanceModifier
        .size(24.dp)
    )

    Text(
      text = LocalContext.current.getString(id),
      modifier = GlanceModifier.padding(start = 16.dp).defaultWeight(),
      style = TextStyle(
        color = FixedColorProvider(color = MaterialTheme.colors.primaryVariant),
        fontSize = 10.sp,
        fontWeight = FontWeight.Bold
      )
    )

    Switch(
      checked = checked,
      onCheckedChange = actionRunCallback<SwitchToggleAction>(
        actionParametersOf(
          toggledSubredditIdKey to id.toString()
        )
      )
    )
  }
}

class SwitchToggleAction : ActionCallback {

  override suspend fun onAction(
    context: Context,
    glanceId: GlanceId,
    parameters: ActionParameters
  ) {
    Log.d("debug_log", "SwitchToggleAction")

    val toggleSubredditId = requireNotNull(parameters[toggledSubredditIdKey])
    val checked = requireNotNull(parameters[ToggleableStateKey])

    updateAppWidgetState(context, glanceId) { state ->
      state[booleanPreferencesKey(toggleSubredditId)] = checked
    }

    context.dataStore.edit {
      Log.d("debug_log", "edit data store")
      Log.d("debug_log", "  toggleSubredditId: $toggleSubredditId: $checked")
      it[booleanPreferencesKey(toggleSubredditId)] = checked
    }

    SubredditsWidget().update(context, glanceId)
  }
}

class SubredditsWidgetReceiver : GlanceAppWidgetReceiver() {

  override val glanceAppWidget: GlanceAppWidget = SubredditsWidget()

  private val coroutineScope = MainScope()
  private var job: Job? = null

  override fun onUpdate(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetIds: IntArray
  ) {
    super.onUpdate(context, appWidgetManager, appWidgetIds)

    Log.d("debug_log", "onUpdate()")
    if (job != null) {
      job!!.cancel()
    }

    job = coroutineScope.launch {

      val glanceId: GlanceId? = GlanceAppWidgetManager(context)
        .getGlanceIds(SubredditsWidget::class.java)
        .firstOrNull()

      if (glanceId != null) {
        withContext(Dispatchers.IO) {
          context.dataStore.data
            .map { preferences ->
              communities.associateWith { communityId ->
                val prefValue = preferences[booleanPreferencesKey(communityId.toString())] ?: false
                prefValue
              }
            }
            .collect { communityToToggle ->
              Log.d("debug_log", "collect data store data")
              communityToToggle.forEach { pair ->
                Log.d("debug_log", "${pair.key} -> ${pair.value}")
                updateAppWidgetState(context, glanceId) { state ->
                  state[booleanPreferencesKey(pair.key.toString())] = pair.value
                }
              }

              Log.d("debug_log", "glanceAppWidget.")
              glanceAppWidget.update(context, glanceId)
            }
        }
      }
    }
  }
}