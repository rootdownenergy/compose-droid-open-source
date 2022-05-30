package com.rootdown.dev.notesapp.root.di

import com.rootdown.dev.notesapp.root.feature_note.data.local.NoteDatabase
import com.rootdown.dev.notesapp.root.feature_note.data.repo.CloudGroupRepoImpl
import com.rootdown.dev.notesapp.root.feature_note.data.repo.NoteRepositoryImpl
import com.rootdown.dev.notesapp.root.feature_note.data.repo.NotesWithCloudGroupsRepoImpl
import com.rootdown.dev.notesapp.root.feature_note.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun provideNoteRepo(db: NoteDatabase): NoteRepositoryImpl {
        return NoteRepositoryImpl(db.noteDao)
    }

    @Provides
    @Singleton
    fun provideCloudGroupRepo(db: NoteDatabase): CloudGroupRepoImpl {
        return CloudGroupRepoImpl(db.cloudGroupDao)
    }

    @Provides
    @Singleton
    fun provideNotesWithCloudGroupsRepo(db: NoteDatabase): NotesWithCloudGroupsRepoImpl {
        return NotesWithCloudGroupsRepoImpl(db.noteWithCloudGroupDao)
    }

    @Provides
    @Singleton
    fun provideNoteUseCases(repository: NoteRepositoryImpl, repo: CloudGroupRepoImpl, repoNotesWithCloudGroup: NotesWithCloudGroupsRepoImpl): NoteUseCases {
        return NoteUseCases(
            getNotes = GetNotes(repository),
            deleteNote = DeleteNote(repository),
            addNote = AddNote(repository),
            getNote = GetNoteById(repository),
            editNote = EditNote(repository),
            addCloudGroup = AddCloudGroup(repo),
            addCrossRef = AddCrossRef(repoNotesWithCloudGroup),
            getNotesWithCloudGroups = GetNoteWithCloudGroup(repoNotesWithCloudGroup),
        )
    }
}