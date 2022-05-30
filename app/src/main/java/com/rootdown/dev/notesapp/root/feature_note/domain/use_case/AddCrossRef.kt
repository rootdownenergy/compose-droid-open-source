package com.rootdown.dev.notesapp.root.feature_note.domain.use_case

import com.rootdown.dev.notesapp.root.feature_note.domain.model.NoteCloudGroupCrossRef
import com.rootdown.dev.notesapp.root.feature_note.data.repo.NotesWithCloudGroupsRepoImpl
import javax.inject.Inject

class AddCrossRef @Inject constructor(
    private val mediator: NotesWithCloudGroupsRepoImpl
) {
    suspend operator fun invoke(crossRef: NoteCloudGroupCrossRef){
        mediator.insertCrossRef(crossRef)
    }
}