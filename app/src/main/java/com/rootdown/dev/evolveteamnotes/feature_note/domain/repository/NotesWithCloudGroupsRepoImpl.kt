package com.rootdown.dev.notesapp.root.feature_note.domain.repository

import android.util.Log
import com.rootdown.dev.notesapp.root.feature_note.data.data_source.NoteWithCloudGroupDao
import com.rootdown.dev.notesapp.root.feature_note.domain.model.NoteCloudGroupCrossRef
import com.rootdown.dev.notesapp.root.feature_note.domain.model.NoteWithCLoudGroups
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

class NotesWithCloudGroupsRepoImpl(
    val dao: NoteWithCloudGroupDao
): NotesWithCloudGroupsRepo {

    override fun getNotesWithCloudGroups(query: Int): List<NoteWithCLoudGroups> {
        return dao.getNotesWithCloudGroups(query)
    }

    override suspend fun insertCrossRef(ref: NoteCloudGroupCrossRef) {
        dao.insertCossRef(ref)
    }

}