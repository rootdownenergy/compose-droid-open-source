package com.rootdown.dev.notesapp.root.feature_note.data.data_source

import androidx.room.*
import com.rootdown.dev.notesapp.root.feature_note.domain.model.NoteCloudGroupCrossRef
import com.rootdown.dev.notesapp.root.feature_note.domain.model.NoteWithCLoudGroups

@Dao
interface NoteWithCloudGroupDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCossRef(ref: NoteCloudGroupCrossRef)
    @Transaction
    @Query("SELECT * FROM note WHERE noteId = :query")
    fun getNotesWithCloudGroups(query: Int): List<NoteWithCLoudGroups>
}