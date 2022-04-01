package com.rootdown.dev.notesapp.root.feature_note.domain.use_case

import com.rootdown.dev.notesapp.root.feature_note.domain.model.NoteWithCLoudGroups
import com.rootdown.dev.notesapp.root.feature_note.domain.repository.NotesWithCloudGroupsRepo

class GetNoteWithCloudGroup(
    private val repository: NotesWithCloudGroupsRepo
) {
    operator fun invoke(query: Int): List<NoteWithCLoudGroups> {
        return repository.getNotesWithCloudGroups(query)
    }
}