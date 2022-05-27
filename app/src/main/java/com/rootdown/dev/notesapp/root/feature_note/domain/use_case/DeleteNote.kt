package com.rootdown.dev.notesapp.root.feature_note.domain.use_case

import com.rootdown.dev.notesapp.root.feature_note.domain.model.Note
import com.rootdown.dev.notesapp.root.feature_note.domain.repository.NoteRepositoryImpl
import javax.inject.Inject

class DeleteNote @Inject constructor(
    private val repository: NoteRepositoryImpl
) {

    suspend operator fun invoke(note: Note) {
        repository.deleteNote(note)
    }
}