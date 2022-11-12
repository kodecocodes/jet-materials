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
package com.yourcompany.android.jetnotes.data.database.dbmapper

import com.yourcompany.android.jetnotes.data.database.model.ColorDbModel
import com.yourcompany.android.jetnotes.data.database.model.NoteDbModel
import com.yourcompany.android.jetnotes.domain.model.ColorModel
import com.yourcompany.android.jetnotes.domain.model.NEW_NOTE_ID
import com.yourcompany.android.jetnotes.domain.model.NoteModel

class DbMapperImpl : DbMapper {

  override fun mapNotes(
    noteDbModels: List<NoteDbModel>,
    colorDbModels: Map<Long, ColorDbModel>
  ): List<NoteModel> = noteDbModels.map {
    val colorDbModel = colorDbModels[it.colorId]
      ?: throw RuntimeException("Color for colorId: ${it.colorId} was not found. Make sure that all colors are passed to this method")

    mapNote(it, colorDbModel)
  }

  override fun mapNote(noteDbModel: NoteDbModel, colorDbModel: ColorDbModel): NoteModel {
    val color = mapColor(colorDbModel)
    val isCheckedOff = with(noteDbModel) { if (canBeCheckedOff) isCheckedOff else null }
    return with(noteDbModel) { NoteModel(id, title, content, isCheckedOff, color) }
  }

  override fun mapColors(colorDbModels: List<ColorDbModel>): List<ColorModel> =
    colorDbModels.map { mapColor(it) }

  override fun mapColor(colorDbModel: ColorDbModel): ColorModel =
    with(colorDbModel) { ColorModel(id, name, hex) }

  override fun mapDbNote(note: NoteModel): NoteDbModel =
    with(note) {
      val canBeCheckedOff = isCheckedOff != null
      val isCheckedOff = isCheckedOff ?: false
      if (id == NEW_NOTE_ID)
        NoteDbModel(
          title = title,
          content = content,
          canBeCheckedOff = canBeCheckedOff,
          isCheckedOff = isCheckedOff,
          colorId = color.id,
          isInTrash = false
        )
      else
        NoteDbModel(id, title, content, canBeCheckedOff, isCheckedOff, color.id, false)
    }

  override fun mapDbColors(colors: List<ColorModel>): List<ColorDbModel> =
    colors.map { mapDbColor(it) }

  override fun mapDbColor(color: ColorModel): ColorDbModel =
    with(color) { ColorDbModel(id, hex, name) }
}
