package com.rootdown.dev.notesapp.root.feature_note.domain.model

import androidx.room.*
import com.rootdown.dev.notesapp.root.feature_note.presentation.theme.*


@Entity
data class Note(
    val title: String,
    val content: String,
    val timestamp: Long,
    val color: Int,
    val ii: String,
){
    @PrimaryKey(autoGenerate = true)
    var noteId: Int = 0
    companion object {
        val noteColors = listOf(RedOrange, LightGreen, Violet, BabyBlue, RedPink)
    }
}

class InvalidNoteException(message: String): Exception(message)