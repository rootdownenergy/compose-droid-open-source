package com.rootdown.dev.notesapp.root.inter_feature.data.local


import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.LiveData
import com.rootdown.dev.notesapp.root.di.util.ApplicationScope
import com.rootdown.dev.notesapp.root.inter_feature.data.model.LocalSourceDatastoreMsg
import com.rootdown.dev.notesapp.root.inter_feature.data.model.LocalSourceDatastoreTheme
import com.rootdown.dev.notesapp.root.inter_feature.data.sub_type_sys.DatastoreState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

const val DATASTORE = "AppDatastore"

val Context.datastore : DataStore<Preferences> by  preferencesDataStore(name = DATASTORE)

class LocalSourceImpl @Inject constructor(
    @ApplicationScope private val context: Context
) : LocalSource {

    val MSG = stringPreferencesKey("msg")
    val SWITCH = booleanPreferencesKey("switch")


    override suspend fun saveMsg(data: LocalSourceDatastoreMsg) {
        context.datastore.edit {  msg ->
            msg[MSG] = data.content
        }
    }

    override suspend fun getMsg() = context.datastore.data.map { msgIn ->
        LocalSourceDatastoreMsg(
            content = msgIn[MSG]!!
        )
    }

    override suspend fun switchTheme(switchSource: LocalSourceDatastoreTheme) {
        context.datastore.edit { switch ->
            switch[SWITCH] = switchSource.flag
        }
    }

    override suspend fun getTheme(): Flow<LocalSourceDatastoreTheme> = context.datastore.data.map { swIn ->
        LocalSourceDatastoreTheme(
            flag = swIn[SWITCH]!!
        )
    }
}