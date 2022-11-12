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
package com.yourcompany.android.jetnotes.data.repository

import com.yourcompany.android.jetnotes.data.database.dao.ColorDao
import com.yourcompany.android.jetnotes.data.database.dao.NoteDao
import com.yourcompany.android.jetnotes.data.database.dbmapper.DbMapper
import com.yourcompany.android.jetnotes.data.database.model.ColorDbModel
import com.yourcompany.android.jetnotes.data.database.model.NoteDbModel
import com.yourcompany.android.jetnotes.domain.model.ColorModel
import com.yourcompany.android.jetnotes.domain.model.NoteModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * {@inheritDoc}
 */
class RepositoryImpl(
  private val noteDao: NoteDao,
  private val colorDao: ColorDao,
  private val dbMapper: DbMapper
) : Repository {

  private val notesNotInTrashLiveData: MutableStateFlow<List<NoteModel>> by lazy {
    MutableStateFlow(emptyList())
  }

  private val notesInTrashLiveData: MutableStateFlow<List<NoteModel>> by lazy {
    MutableStateFlow(emptyList())
  }

  init {
    initDatabase(this::updateNotesLiveData)
  }

  /**
   * Populates database with colors if it is empty.
   */
  private fun initDatabase(postInitAction: suspend () -> Unit) {
    GlobalScope.launch {
      // Prepopulate colors
      val colors = ColorDbModel.DEFAULT_COLORS.toTypedArray()
      val dbColors = colorDao.getAllSync()
      if (dbColors.isEmpty()) {
        colorDao.insertAll(*colors)
      }

      // Prepopulate notes
      val notes = NoteDbModel.DEFAULT_NOTES.toTypedArray()
      val dbNotes = noteDao.getAllSync()
      if (dbNotes.isEmpty()) {
        noteDao.insertAll(*notes)
      }

      postInitAction.invoke()
    }
  }

  override fun getAllNotesNotInTrash(): Flow<List<NoteModel>> = notesNotInTrashLiveData

  override fun getAllNotesInTrash(): Flow<List<NoteModel>> = notesInTrashLiveData

  private suspend fun getAllNotesDependingOnTrashStateSync(inTrash: Boolean): List<NoteModel> {
    val colorDbModels: Map<Long, ColorDbModel> = colorDao.getAllSync().associateBy { it.id }
    val dbNotesNotInTrash: List<NoteDbModel> =
      noteDao.getAllSync().filter { it.isInTrash == inTrash }
    return dbMapper.mapNotes(dbNotesNotInTrash, colorDbModels)
  }

  override fun getNote(id: Long): Flow<NoteModel> = noteDao.findById(id)
    .map {
      val colorDbModel = colorDao.findByIdSync(it.colorId)
      dbMapper.mapNote(it, colorDbModel)
    }

  override suspend fun insertNote(note: NoteModel) {
    noteDao.insert(dbMapper.mapDbNote(note))
    updateNotesLiveData()
  }

  override suspend fun deleteNote(id: Long) {
    noteDao.delete(id)
    updateNotesLiveData()
  }

  override suspend fun deleteNotes(noteIds: List<Long>) {
    noteDao.delete(noteIds)
    updateNotesLiveData()
  }

  override suspend fun moveNoteToTrash(noteId: Long) {
    val dbNote = noteDao.findByIdSync(noteId)
    val newDbNote = dbNote.copy(isInTrash = true)
    noteDao.insert(newDbNote)
    updateNotesLiveData()
  }

  override suspend fun restoreNotesFromTrash(noteIds: List<Long>) {
    val dbNotesInTrash = noteDao.getNotesByIdsSync(noteIds)
    dbNotesInTrash.forEach {
      val newDbNote = it.copy(isInTrash = false)
      noteDao.insert(newDbNote)
    }
    updateNotesLiveData()
  }

  override fun getAllColors(): Flow<List<ColorModel>> = colorDao.getAll()
    .map { dbMapper.mapColors(it) }

  override suspend fun getAllColorsSync(): List<ColorModel> =
    dbMapper.mapColors(colorDao.getAllSync())

  override fun getColor(id: Long): Flow<ColorModel> = colorDao.findById(id)
    .map { dbMapper.mapColor(it) }

  override suspend fun getColorSync(id: Long): ColorModel =
    dbMapper.mapColor(colorDao.findByIdSync(id))

  private suspend fun updateNotesLiveData() {
    notesNotInTrashLiveData.update { getAllNotesDependingOnTrashStateSync(inTrash = false) }
    notesInTrashLiveData.update { getAllNotesDependingOnTrashStateSync(inTrash = true) }
  }
}