/*
 * Copyright (c) 2021 Kodeco Inc.
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
package com.yourcompany.android.jetnotes.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.res.painterResource
import com.yourcompany.android.jetnotes.R
import com.yourcompany.android.jetnotes.domain.model.NoteModel
import com.yourcompany.android.jetnotes.ui.components.Note
import com.yourcompany.android.jetnotes.viewmodel.MainViewModel

private const val NO_DIALOG = 1
private const val RESTORE_NOTES_DIALOG = 2
private const val PERMANENTLY_DELETE_DIALOG = 3

@Composable
@ExperimentalMaterialApi
fun TrashScreen(
  viewModel: MainViewModel,
  onTopBarNavigationIconClicked: () -> Unit
) {

  val notesInThrash: List<NoteModel> by viewModel.notesInTrash
    .observeAsState(listOf())

  val selectedNotes: List<NoteModel> by viewModel.selectedNotes
    .observeAsState(listOf())

  val dialogState: MutableState<Int> = rememberSaveable { mutableStateOf(NO_DIALOG) }

  Column {
    val areActionsVisible = selectedNotes.isNotEmpty()
    TrashTopAppBar(
      onNavigationIconClick = onTopBarNavigationIconClicked,
      onRestoreNotesClick = { dialogState.value = RESTORE_NOTES_DIALOG },
      onDeleteNotesClick = { dialogState.value = PERMANENTLY_DELETE_DIALOG },
      areActionsVisible = areActionsVisible
    )
    Content(
      notes = notesInThrash,
      onNoteClick = { viewModel.onNoteSelected(it) },
      selectedNotes = selectedNotes
    )

    val dialog = dialogState.value
    if (dialog != NO_DIALOG) {
      val confirmAction: () -> Unit = when (dialog) {
        RESTORE_NOTES_DIALOG -> {
          {
            viewModel.restoreNotes(selectedNotes)
            dialogState.value = NO_DIALOG
          }
        }
        PERMANENTLY_DELETE_DIALOG -> {
          {
            viewModel.permanentlyDeleteNotes(selectedNotes)
            dialogState.value = NO_DIALOG
          }
        }
        else -> {
          {
            dialogState.value = NO_DIALOG
          }
        }
      }

      AlertDialog(
        onDismissRequest = { dialogState.value = NO_DIALOG },
        title = { Text(mapDialogTitle(dialog)) },
        text = { Text(mapDialogText(dialog)) },
        confirmButton = {
          TextButton(onClick = confirmAction) {
            Text("Confirm")
          }
        },
        dismissButton = {
          TextButton(onClick = { dialogState.value = NO_DIALOG }) {
            Text("Dismiss")
          }
        }
      )
    }
  }
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
        Icon(
          imageVector = Icons.Filled.List,
          contentDescription = "Drawer Button"
        )
      }
    },
    actions = {
      if (areActionsVisible) {
        IconButton(onClick = onRestoreNotesClick) {
          Icon(
            painter = painterResource(id = R.drawable.ic_baseline_restore_from_trash_24),
            contentDescription = "Restore Notes Button",
            tint = MaterialTheme.colors.onPrimary
          )
        }
        IconButton(onClick = onDeleteNotesClick) {
          Icon(
            painter = painterResource(id = R.drawable.ic_baseline_delete_forever_24),
            contentDescription = "Delete Notes Button",
            tint = MaterialTheme.colors.onPrimary
          )
        }
      }
    }
  )
}

@Composable
@ExperimentalMaterialApi
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

    LazyColumn {
      items(count = filteredNotes.size) { noteIndex ->
        val note = filteredNotes[noteIndex]
        val isNoteSelected = selectedNotes.contains(note)
        Note(
          note = note,
          onNoteClick = onNoteClick,
          isSelected = isNoteSelected
        )
      }
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