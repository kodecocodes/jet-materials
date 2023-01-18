package com.yourcompany.android.jetreddit.appwidget

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.glance.*
import androidx.glance.action.ActionParameters
import androidx.glance.action.actionParametersOf
import androidx.glance.appwidget.CheckBox
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.action.ActionCallback
import androidx.glance.appwidget.action.ToggleableStateKey
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.appwidget.appWidgetBackground
import androidx.glance.appwidget.lazy.LazyColumn
import androidx.glance.appwidget.lazy.items
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.glance.layout.Column
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.padding
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import com.yourcompany.android.jetreddit.R

class SubredditsWidget : GlanceAppWidget() {

  @Composable
  override fun Content() {
    GlanceTheme {
      Column(
        modifier = GlanceModifier
          .fillMaxSize()
          .padding(16.dp)
          .appWidgetBackground()
          .background(GlanceTheme.colors.background)
          .appWidgetBackgroundCornerRadius()
      ) {
        Text(
          text = LocalContext.current.getString(R.string.glance_todo_list),
          modifier = GlanceModifier
            .fillMaxWidth()
            .padding(8.dp),
          style = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = GlanceTheme.colors.primary
          ),
        )
        CountChecked()
        LazyColumn {
          items(groceryStringIds) { id ->
            CheckBoxItem(id)
          }
        }
      }
    }
  }
}

@Composable
private fun CheckBoxItem(id: Int) {
  val prefs = currentState<Preferences>()
  val checked = prefs[booleanPreferencesKey(id.toString())] ?: false
  CheckBox(
    text = LocalContext.current.getString(id),
    checked = checked,
    onCheckedChange = actionRunCallback<CheckboxClickAction>(
      actionParametersOf(
        toggledStringIdKey to id.toString(),
      )
    ),
    modifier = GlanceModifier.padding(12.dp),
    style = TextStyle(color = GlanceTheme.colors.textColorPrimary),
  )
}

@Composable
private fun CountChecked() {
  val prefs = currentState<Preferences>()
  val checkedCount = groceryStringIds.filter {
    prefs[booleanPreferencesKey(it.toString())] ?: false
  }.size

  Text(
    text = "$checkedCount checkboxes checked",
    modifier = GlanceModifier.padding(start = 8.dp),
    style = TextStyle(
      color = GlanceTheme.colors.textColorSecondary
    )
  )
}

private val toggledStringIdKey = ActionParameters.Key<String>("ToggledStringIdKey")

private val groceryStringIds = listOf(
  R.string.grocery_list_milk,
  R.string.grocery_list_eggs,
  R.string.grocery_list_tomatoes,
  R.string.grocery_list_bacon,
  R.string.grocery_list_butter,
  R.string.grocery_list_cheese,
  R.string.grocery_list_potatoes,
  R.string.grocery_list_broccoli,
  R.string.grocery_list_salmon,
  R.string.grocery_list_yogurt
)

class CheckboxClickAction : ActionCallback {
  override suspend fun onAction(
    context: Context,
    glanceId: GlanceId,
    parameters: ActionParameters
  ) {
    val toggledStringId = requireNotNull(parameters[toggledStringIdKey]) {
      "Add $toggledStringIdKey parameter in the ActionParameters."
    }

    // The checked state of the clicked checkbox can be added implicitly to the parameters and
    // can be retrieved by using the ToggleableStateKey
    val checked = requireNotNull(parameters[ToggleableStateKey]) {
      "This action should only be called in response to toggleable events"
    }
    updateAppWidgetState(context, glanceId) { state ->
      state[booleanPreferencesKey(toggledStringId)] = checked
    }
    SubredditsWidget().update(context, glanceId)
  }
}

class SubredditsWidgetReceiver : GlanceAppWidgetReceiver() {

  override val glanceAppWidget: GlanceAppWidget = SubredditsWidget()
}