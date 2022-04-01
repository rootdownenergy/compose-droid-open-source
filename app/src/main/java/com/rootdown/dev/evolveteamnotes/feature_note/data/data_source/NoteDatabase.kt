package com.rootdown.dev.notesapp.root.feature_note.data.data_source

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rootdown.dev.notesapp.root.feature_note.domain.model.*

@Database(
    entities = [Note::class, CloudGroup::class, NoteCloudGroupCrossRef::class],
    version = 1
)
abstract class NoteDatabase: RoomDatabase() {

    abstract val noteDao: NoteDao
    abstract val cloudGroupDao: CloudGroupDao
    abstract val noteWithCloudGroupDao: NoteWithCloudGroupDao

    companion object {
        @Volatile
        private var INSTANCE: NoteDatabase? = null
        const val DATABASE_NAME = "story_share_db"
        fun getInstance(context: Context): NoteDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                NoteDatabase::class.java, DATABASE_NAME)
                .build()
    }
}