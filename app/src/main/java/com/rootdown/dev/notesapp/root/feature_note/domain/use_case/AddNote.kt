package com.rootdown.dev.notesapp.root.feature_note.domain.use_case

import com.rootdown.dev.notesapp.root.feature_note.domain.model.InvalidNoteException
import com.rootdown.dev.notesapp.root.feature_note.domain.model.Note
import com.rootdown.dev.notesapp.root.feature_note.data.repo.NoteRepositoryImpl
import javax.inject.Inject

class AddNote @Inject constructor(
    private val repository: NoteRepositoryImpl
) {
    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note: Note) {
        if(note.title.isBlank()) {
            throw InvalidNoteException("The title of the note cannot be blank")
        }
        if(note.content.isBlank()) {
            throw InvalidNoteException("The content of the note cannot be blank")
        }
        repository.insertNote(note)
    }
}