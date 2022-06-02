package com.rootdown.dev.notesapp.root.feature_note.domain.model

import androidx.room.*
import com.rootdown.dev.notesapp.root.inter_feature.presentation.theme.*

@Entity(
    tableName = "note"
)
data class Note(
    @PrimaryKey(autoGenerate = true)
    var noteId: Int = 0,
    var title: String = "",
    var content: String = "",
    var timestamp: Long = 0L,
    var color: Int = 0,
    var ii: Int = 0,
) {
    companion object {
        val noteColors = listOf(primary, secondary, primaryLight, secondaryLight, primaryText, secondaryText)
    }
}

class InvalidNoteException(message: String): Exception(message)