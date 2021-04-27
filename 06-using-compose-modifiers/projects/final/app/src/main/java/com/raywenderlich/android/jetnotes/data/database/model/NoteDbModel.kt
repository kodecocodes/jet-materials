/*
 * Copyright (c) 2021 Razeware LLC
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
package com.raywenderlich.android.jetnotes.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NoteDbModel(
  @PrimaryKey(autoGenerate = true) val id: Long = 0,
  @ColumnInfo(name = "title") val title: String,
  @ColumnInfo(name = "content") val content: String,
  @ColumnInfo(name = "can_be_checked_off") val canBeCheckedOff: Boolean,
  @ColumnInfo(name = "is_checked_off") val isCheckedOff: Boolean,
  @ColumnInfo(name = "color_id") val colorId: Long,
  @ColumnInfo(name = "in_trash") val isInTrash: Boolean
) {

  companion object {

    val DEFAULT_NOTES = listOf(
      NoteDbModel(1, "RW Meeting", "Prepare sample project", false, false, 1, false),
      NoteDbModel(2, "Bills", "Pay by tomorrow", false, false, 2, false),
      NoteDbModel(3, "Pancake recipe", "Milk, eggs, salt, flour...", false, false, 3, false),
      NoteDbModel(4, "Workout", "Running, push ups, pull ups, squats...", false, false, 4, false),
      NoteDbModel(5, "Title 5", "Content 5", false, false, 5, false),
      NoteDbModel(6, "Title 6", "Content 6", false, false, 6, false),
      NoteDbModel(7, "Title 7", "Content 7", false, false, 7, false),
      NoteDbModel(8, "Title 8", "Content 8", false, false, 8, false),
      NoteDbModel(9, "Title 9", "Content 9", false, false, 9, false),
      NoteDbModel(10, "Title 10", "Content 10", false, false, 10, false),
      NoteDbModel(11, "Title 11", "Content 11", true, false, 11, false),
      NoteDbModel(12, "Title 12", "Content 12", true, false, 12, false)
    )
  }
}
