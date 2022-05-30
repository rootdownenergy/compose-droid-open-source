package com.rootdown.dev.notesapp.root.feature_note.domain.use_case

import com.rootdown.dev.notesapp.root.feature_note.domain.model.InvalidNoteException
import com.rootdown.dev.notesapp.root.feature_note.domain.model.Note
import com.rootdown.dev.notesapp.root.feature_note.data.repo.NoteRepositoryImpl
import javax.inject.Inject

class EditNote @Inject constructor(
    private val repo: NoteRepositoryImpl
) {
    @Throws(InvalidNoteException::class)
    operator fun invoke(note: Note) {
        if(note.title.isBlank()) {
            throw InvalidNoteException("The title of the note cannot be blank")
        }
        if(note.content.isBlank()) {
            throw InvalidNoteException("The content of the note cannot be blank")
        }
        if(note.noteId.equals(null)) {
            throw InvalidNoteException("The id of the note cannot be blank")
        }
        if(note.ii.equals(null)) {
            throw InvalidNoteException("The id of the note cannot be blank")
        }
        //repo.updateNote(note)
    }
}