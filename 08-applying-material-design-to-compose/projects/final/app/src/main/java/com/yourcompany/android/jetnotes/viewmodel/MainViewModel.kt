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
package com.yourcompany.android.jetnotes.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.yourcompany.android.jetnotes.data.repository.Repository
import com.yourcompany.android.jetnotes.domain.model.NoteModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

import com.yourcompany.android.jetnotes.domain.model.ColorModel

/**
 * View model used for storing the global app state.
 *
 * This view model is used for all screens.
 */
class MainViewModel(private val repository: Repository) : ViewModel() {

  val notesNotInTrash: LiveData<List<NoteModel>> by lazy {
    repository.getAllNotesNotInTrash().asLiveData()
  }

  private var _noteEntry = MutableStateFlow<NoteModel>(NoteModel())
  val noteEntry: LiveData<NoteModel> = _noteEntry.asLiveData()

  val colors: LiveData<List<ColorModel>> by lazy {
    repository.getAllColors().asLiveData()
  }

  val notesInTrash: LiveData<List<NoteModel>> by lazy {
    repository.getAllNotesInTrash().asLiveData()
  }

  private var _selectedNotes = MutableStateFlow<List<NoteModel>>(listOf())
  val selectedNotes: LiveData<List<NoteModel>> = _selectedNotes.asLiveData()

  fun onCreateNewNoteClick() {
    _noteEntry.value = NoteModel()
  }

  fun onNoteClick(note: NoteModel) {
    _noteEntry.value = note
  }

  fun onNoteCheckedChange(note: NoteModel) {
    viewModelScope.launch(Dispatchers.Default) {
      repository.insertNote(note)
    }
  }

  fun onNoteSelected(note: NoteModel) {
    _selectedNotes.value = _selectedNotes.value!!.toMutableList().apply {
      if (contains(note)) {
        remove(note)
      } else {
        add(note)
      }
    }
  }

  fun restoreNotes(notes: List<NoteModel>) {
    viewModelScope.launch(Dispatchers.Default) {
      repository.restoreNotesFromTrash(notes.map { it.id })
      withContext(Dispatchers.Main) {
        _selectedNotes.value = listOf()
      }
    }
  }

  fun permanentlyDeleteNotes(notes: List<NoteModel>) {
    viewModelScope.launch(Dispatchers.Default) {
      repository.deleteNotes(notes.map { it.id })
      withContext(Dispatchers.Main) {
        _selectedNotes.value = listOf()
      }
    }
  }

  fun onNoteEntryChange(note: NoteModel) {
    _noteEntry.value = note
  }

  fun saveNote(note: NoteModel) {
    viewModelScope.launch(Dispatchers.Default) {
      repository.insertNote(note)

      withContext(Dispatchers.Main) {
        _noteEntry.value = NoteModel()
      }
    }
  }

  fun moveNoteToTrash(note: NoteModel) {
    viewModelScope.launch(Dispatchers.Default) {
      repository.moveNoteToTrash(note.id)
    }
  }
}
