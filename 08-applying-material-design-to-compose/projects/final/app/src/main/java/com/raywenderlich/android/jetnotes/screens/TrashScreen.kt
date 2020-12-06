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
package com.raywenderlich.android.jetnotes.screens

import androidx.compose.foundation.Icon
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.savedinstancestate.savedInstanceState
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.raywenderlich.android.jetnotes.R
import com.raywenderlich.android.jetnotes.domain.model.NoteModel
import com.raywenderlich.android.jetnotes.routing.Screen
import com.raywenderlich.android.jetnotes.ui.components.AppDrawer
import com.raywenderlich.android.jetnotes.ui.components.Note
import com.raywenderlich.android.jetnotes.viewmodel.MainViewModel
import java.lang.RuntimeException

private const val NO_DIALOG = 1
private const val RESTORE_NOTES_DIALOG = 2
private const val PERMANENTLY_DELETE_DIALOG = 3

@Composable
fun TrashScreen(viewModel: MainViewModel) {

  val notesInThrash: List<NoteModel> by viewModel.notesInThrash
    .observeAsState(listOf())

  val selectedNotes: List<NoteModel> by viewModel.selectedNotes
    .observeAsState(listOf())

  var dialog: Int by savedInstanceState { NO_DIALOG }

  val scaffoldState: ScaffoldState = rememberScaffoldState()

  Scaffold(
    topBar = {
      val areActionsVisible = selectedNotes.isNotEmpty()
      TrashTopAppBar(
        onNavigationIconClick = { scaffoldState.drawerState.open() },
        onRestoreNotesClick = { dialog = RESTORE_NOTES_DIALOG },
        onDeleteNotesClick = { dialog = PERMANENTLY_DELETE_DIALOG },
        areActionsVisible = areActionsVisible
      )
    },
    scaffoldState = scaffoldState,
    drawerContent = {
      AppDrawer(
        currentScreen = Screen.Trash,
        closeDrawerAction = { scaffoldState.drawerState.close() }
      )
    },
    bodyContent = {
      Content(
        notes = notesInThrash,
        onNoteClick = { viewModel.onNoteSelected(it) },
        selectedNotes = selectedNotes
      )

      if (dialog != NO_DIALOG) {
        val confirmAction: () -> Unit = when (dialog) {
          RESTORE_NOTES_DIALOG -> {
            {
              viewModel.restoreNotes(selectedNotes)
              dialog = NO_DIALOG
            }
          }
          PERMANENTLY_DELETE_DIALOG -> {
            {
              viewModel.permanentlyDeleteNotes(selectedNotes)
              dialog = NO_DIALOG
            }
          }
          else -> {
            {
              dialog = NO_DIALOG
            }
          }
        }

        AlertDialog(
          onDismissRequest = {
            dialog = NO_DIALOG
          },
          title = { Text(mapDialogTitle(dialog)) },
          text = { Text(mapDialogText(dialog)) },
          confirmButton = {
            TextButton(onClick = confirmAction) {
              Text("Confirm")
            }
          },
          dismissButton = {
            TextButton(onClick = { dialog = NO_DIALOG }) {
              Text("Dismiss")
            }
          }
        )
      }
    }
  )
}

@Composable
private fun TrashTopAppBar(
  onNavigationIconClick: () -> Unit,
  onRestoreNotesClick: () -> Unit,
  onDeleteNotesClick: () -> Unit,
  areActionsVisible: Boolean
) {
  TopAppBar(
    title = { Text(text = "Trash", color = MaterialTheme.colors.onPrimary) },
    navigationIcon = {
      IconButton(onClick = onNavigationIconClick) {
        Icon(Icons.Filled.List)
      }
    },
    actions = {
      if (areActionsVisible) {
        IconButton(onClick = onRestoreNotesClick) {
          Icon(
            asset = vectorResource(id = R.drawable.ic_baseline_restore_from_trash_24),
            tint = MaterialTheme.colors.onPrimary
          )
        }
        IconButton(onClick = onDeleteNotesClick) {
          Icon(
            asset = vectorResource(id = R.drawable.ic_baseline_delete_forever_24),
            tint = MaterialTheme.colors.onPrimary
          )
        }
      }
    }
  )
}

@Composable
private fun Content(
  notes: List<NoteModel>,
  onNoteClick: (NoteModel) -> Unit,
  selectedNotes: List<NoteModel>,
) {
  val tabs = listOf("REGULAR", "CHECKABLE")

  // Init state for selected tab
  var selectedTab by remember { mutableStateOf(0) }

  Column {
    TabRow(selectedTabIndex = selectedTab) {
      tabs.forEachIndexed { index, title ->
        Tab(
          text = { Text(title) },
          selected = selectedTab == index,
          onClick = { selectedTab = index }
        )
      }
    }

    val filteredNotes = when (selectedTab) {
      0 -> {
        notes.filter { it.isCheckedOff == null }
      }
      1 -> {
        notes.filter { it.isCheckedOff != null }
      }
      else -> throw IllegalStateException("Tab not supported - index: $selectedTab")
    }

    LazyColumnFor(items = filteredNotes) { note ->
      val isLast = filteredNotes.last() == note
      val defaultPadding = 8.dp
      val bottomPadding = if (isLast) 96.dp else 0.dp

      val background = if (selectedNotes.contains(note))
        androidx.compose.ui.graphics.Color.LightGray
      else
        MaterialTheme.colors.surface

      Note(
        note = note,
        onNoteClick = onNoteClick
      )
    }
  }
}

private fun mapDialogTitle(dialog: Int): String = when (dialog) {
  RESTORE_NOTES_DIALOG -> "Restore notes"
  PERMANENTLY_DELETE_DIALOG -> "Delete notes forever"
  else -> throw RuntimeException("Dialog not supported: $dialog")
}

private fun mapDialogText(dialog: Int): String = when (dialog) {
  RESTORE_NOTES_DIALOG -> "Are you sure you want to restore selected notes?"
  PERMANENTLY_DELETE_DIALOG -> "Are you sure you want to delete selected notes permanently?"
  else -> throw RuntimeException("Dialog not supported: $dialog")
}