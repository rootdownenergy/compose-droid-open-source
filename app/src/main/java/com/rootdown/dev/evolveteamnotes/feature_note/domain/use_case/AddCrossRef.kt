package com.rootdown.dev.notesapp.root.feature_note.domain.use_case

import com.rootdown.dev.notesapp.root.feature_note.domain.model.NoteCloudGroupCrossRef
import com.rootdown.dev.notesapp.root.feature_note.domain.repository.NotesWithCloudGroupsRepo

class AddCrossRef(
    private val mediator: NotesWithCloudGroupsRepo
) {
    suspend operator fun invoke(crossRef: NoteCloudGroupCrossRef){
        mediator.insertCrossRef(crossRef)
    }
}