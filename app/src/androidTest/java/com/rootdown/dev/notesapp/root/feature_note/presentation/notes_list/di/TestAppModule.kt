package com.rootdown.dev.notesapp.root.feature_note.presentation.notes_list.di

import android.app.Application
import androidx.room.Room
import com.rootdown.dev.notesapp.root.feature_note.data.local.NoteDatabase
import com.rootdown.dev.notesapp.root.feature_note.data.repo.*
import com.rootdown.dev.notesapp.root.feature_note.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): NoteDatabase {
        return Room.inMemoryDatabaseBuilder(
            app,
            NoteDatabase::class.java,
        ).build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(db: NoteDatabase): NoteRepository {
        return NoteRepositoryImpl(db.noteDao)
    }

    @Provides
    @Singleton
    fun provideNoteUseCases(
        repository: NoteRepositoryImpl,
        repo: CloudGroupRepoImpl,
        repoNotesWithCloudGroup: NotesWithCloudGroupsRepoImpl
    ): NoteUseCases {
        return NoteUseCases(
            getNotes = GetNotes(repository),
            deleteNote = DeleteNote(repository),
            editNote = EditNote(repository),
            addNote = AddNote(repository),
            getNote = GetNoteById(repository),
            addCrossRef = AddCrossRef(repoNotesWithCloudGroup),
            getNotesWithCloudGroups = GetNoteWithCloudGroup(repoNotesWithCloudGroup),
            addCloudGroup = AddCloudGroup(repo)
        )
    }
}