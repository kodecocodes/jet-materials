package com.raywenderlich.android.jetnotes.ui.screens

import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.savedinstancestate.savedInstanceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.raywenderlich.android.jetnotes.R
import com.raywenderlich.android.jetnotes.domain.model.ColorModel
import com.raywenderlich.android.jetnotes.domain.model.NEW_NOTE_ID
import com.raywenderlich.android.jetnotes.domain.model.NoteModel
import com.raywenderlich.android.jetnotes.routing.JetNotesRouter
import com.raywenderlich.android.jetnotes.routing.Screen
import com.raywenderlich.android.jetnotes.ui.components.NoteColor
import com.raywenderlich.android.jetnotes.util.BackPressHandler
import com.raywenderlich.android.jetnotes.util.fromHex
import com.raywenderlich.android.jetnotes.viewmodel.MainViewModel

@Composable
fun SaveNoteScreen(viewModel: MainViewModel) {

  val noteEntry: NoteModel by viewModel.noteEntry
    .observeAsState(NoteModel())

  val colors: List<ColorModel> by viewModel.colors
    .observeAsState(listOf())

  val bottomDrawerState: BottomDrawerState =
    rememberBottomDrawerState(BottomDrawerValue.Closed)

  BackPressHandler(onBackPressed = {
    if (bottomDrawerState.isOpen) {
      bottomDrawerState.close()
    } else {
      JetNotesRouter.navigateTo(Screen.Notes)
    }
  })

  val moveNoteToTrashDialogShownState: MutableState<Boolean> = savedInstanceState { false }

  Scaffold(
    topBar = {
      val isEditingMode: Boolean = noteEntry.id != NEW_NOTE_ID
      SaveNoteTopAppBar(
        isEditingMode = isEditingMode,
        onBackClick = {
          JetNotesRouter.navigateTo(Screen.Notes)
        },
        onSaveNoteClick = { viewModel.saveNote(noteEntry) },
        onOpenColorPickerClick = { bottomDrawerState.open() },
        onDeleteNoteClick = {
          moveNoteToTrashDialogShownState.value = true
        }
      )
    },
    bodyContent = {
      BottomDrawerLayout(
        drawerState = bottomDrawerState,
        drawerContent = {
          ColorPicker(
            colors = colors,
            onColorSelect = { color ->
              val newNoteEntry = noteEntry.copy(color = color)
              viewModel.onNoteEntryChange(newNoteEntry)
            }
          )
        },
        bodyContent = {
          SaveNoteContent(
            note = noteEntry,
            onNoteChange = { updateNoteEntry ->
              viewModel.onNoteEntryChange(updateNoteEntry)
            }
          )

          if (moveNoteToTrashDialogShownState.value) {
            AlertDialog(
              onDismissRequest = {
                moveNoteToTrashDialogShownState.value = false
              },
              title = {
                Text("Move note to the trash?")
              },
              text = {
                Text(
                  "Are you sure you want to " +
                      "move this note to the trash?"
                )
              },
              confirmButton = {
                TextButton(onClick = {
                  viewModel.moveNoteToTrash(noteEntry)
                }) {
                  Text("Confirm")
                }
              },
              dismissButton = {
                TextButton(onClick = {
                  moveNoteToTrashDialogShownState.value = false
                }) {
                  Text("Dismiss")
                }
              }
            )
          }
        }
      )
    }
  )
}

@Composable
private fun SaveNoteTopAppBar(
  isEditingMode: Boolean,
  onBackClick: () -> Unit,
  onSaveNoteClick: () -> Unit,
  onOpenColorPickerClick: () -> Unit,
  onDeleteNoteClick: () -> Unit
) {
  TopAppBar(
    title = {
      Text(
        text = "Save Note",
        color = MaterialTheme.colors.onPrimary
      )
    },
    navigationIcon = {
      IconButton(onClick = onBackClick) {
        Icon(imageVector = Icons.Filled.ArrowBack)
      }
    },
    actions = {
      // Save note action icon
      IconButton(onClick = onSaveNoteClick) {
        Icon(
          imageVector = Icons.Default.Check,
          tint = MaterialTheme.colors.onPrimary
        )
      }

      // Open color picker action icon
      IconButton(onClick = onOpenColorPickerClick) {
        Icon(
          imageVector = vectorResource(
            id = R.drawable.ic_baseline_color_lens_24
          ),
          tint = MaterialTheme.colors.onPrimary
        )
      }

      if (isEditingMode) {
        IconButton(onClick = onDeleteNoteClick) {
          Icon(
            imageVector = Icons.Default.Delete,
            tint = MaterialTheme.colors.onPrimary
          )
        }
      }
    }
  )
}

@Composable
private fun SaveNoteContent(
  note: NoteModel,
  onNoteChange: (NoteModel) -> Unit
) {
  Column(modifier = Modifier.fillMaxSize()) {
    ContentTextField(
      label = "Title",
      text = note.title,
      onTextChange = { newTitle ->
        onNoteChange.invoke(note.copy(title = newTitle))
      }
    )

    ContentTextField(
      modifier = Modifier
        .heightIn(max = 240.dp)
        .padding(top = 16.dp),
      label = "Body",
      text = note.content,
      onTextChange = { newContent ->
        onNoteChange.invoke(note.copy(content = newContent))
      }
    )

    val canBeCheckedOff: Boolean = note.isCheckedOff != null
    NoteCheckOption(
      isChecked = canBeCheckedOff,
      onCheckedChange = { canBeCheckedOffNewValue ->
        val isCheckedOff: Boolean? =
          if (canBeCheckedOffNewValue) false else null
        onNoteChange.invoke(
          note.copy(isCheckedOff = isCheckedOff)
        )
      }
    )

    PickedColor(color = note.color)
  }
}


@Composable
private fun ContentTextField(
  modifier: Modifier = Modifier,
  label: String,
  text: String,
  onTextChange: (String) -> Unit
) {
  TextField(
    value = text,
    onValueChange = onTextChange,
    label = { Text(label) },
    modifier = modifier
      .fillMaxWidth()
      .padding(horizontal = 8.dp),
    backgroundColor = MaterialTheme.colors.surface
  )
}

@Composable
private fun PickedColor(color: ColorModel) {
  Row(
    Modifier
      .padding(8.dp)
      .padding(top = 16.dp)
  ) {
    Text(
      text = "Picked color",
      modifier = Modifier
        .weight(1f)
        .align(Alignment.CenterVertically)
    )
    NoteColor(
      color = Color.fromHex(color.hex),
      size = 40.dp,
      border = 1.dp,
      modifier = Modifier.padding(4.dp)
    )
  }
}

@Composable
private fun NoteCheckOption(
  isChecked: Boolean,
  onCheckedChange: (Boolean) -> Unit
) {
  Row(
    Modifier
      .padding(8.dp)
      .padding(top = 16.dp)
  ) {
    Text(
      text = "Can note be checked off?",
      modifier = Modifier.weight(1f)
    )
    Switch(
      checked = isChecked,
      onCheckedChange = onCheckedChange,
      modifier = Modifier.padding(start = 8.dp)
    )
  }
}

@Composable
private fun ColorPicker(
  colors: List<ColorModel>,
  onColorSelect: (ColorModel) -> Unit
) {
  Column(modifier = Modifier.fillMaxWidth()) {
    Text(
      text = "Color picker",
      fontSize = 18.sp,
      fontWeight = FontWeight.Bold,
      modifier = Modifier.padding(8.dp)
    )
    ScrollableColumn(modifier = Modifier.fillMaxWidth()) {
      for (color in colors) {
        ColorItem(color, onColorSelect)
      }
    }
  }
}

@Composable
fun ColorItem(
  color: ColorModel,
  onColorSelect: (ColorModel) -> Unit
) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .clickable(
        onClick = {
          onColorSelect.invoke(color)
        }
      )
  ) {
    NoteColor(
      Color.fromHex(color.hex),
      size = 80.dp,
      border = 2.dp,
      modifier = Modifier.padding(10.dp)
    )
    Text(
      text = color.name,
      fontSize = 22.sp,
      modifier = Modifier
        .padding(horizontal = 16.dp)
        .align(Alignment.CenterVertically)
    )
  }
}

@Preview
@Composable
fun ColorPickerPreview() {
  ColorPicker(
    colors = listOf(
      ColorModel.DEFAULT,
      ColorModel.DEFAULT,
      ColorModel.DEFAULT
    )
  ) { }
}

@Preview
@Composable
fun ColorItemPreview() {
  ColorItem(ColorModel.DEFAULT) {}
}

@Preview
@Composable
fun SaveNoteTopAppBarPreview() {
  SaveNoteTopAppBar(
    isEditingMode = true,
    onBackClick = {},
    onSaveNoteClick = {},
    onOpenColorPickerClick = {},
    onDeleteNoteClick = {}
  )
}

@Preview
@Composable
fun PickedColorPreview() {
  PickedColor(ColorModel.DEFAULT)
}

@Preview
@Composable
fun NoteCheckOptionPreview() {
  NoteCheckOption(false) {}
}

@Preview
@Composable
fun ContentTextFieldPreview() {
  ContentTextField(
    label = "Title",
    text = "",
    onTextChange = {}
  )
}

@Preview
@Composable
fun SaveNoteContentPreview() {
  SaveNoteContent(
    note = NoteModel(title = "Title", content = "content"),
    onNoteChange = {}
  )
}