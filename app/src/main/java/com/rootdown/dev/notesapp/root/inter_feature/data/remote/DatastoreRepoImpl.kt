package com.rootdown.dev.notesapp.root.inter_feature.data.remote

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.rootdown.dev.notesapp.root.inter_feature.data.model.LocalSourceDatastoreMsg
import com.rootdown.dev.notesapp.root.inter_feature.data.model.LocalSourceDatastoreTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DatastoreRepoImpl @Inject constructor(
    private val datastore: DataStore<Preferences>
) {
    suspend fun saveMsg(data: LocalSourceDatastoreMsg) {
        datastore.edit {  msg ->
            msg[MSG] = data.content
        }
    }

    fun getMsg() = datastore.data.map { msgIn ->
        LocalSourceDatastoreMsg(
            content = msgIn[MSG] ?: ""
        )
    }

    suspend fun switchTheme(theme: LocalSourceDatastoreTheme) {
        datastore.edit { switch ->
            switch[SWITCH] = theme.flag
        }
    }

    fun getTheme(): Flow<LocalSourceDatastoreTheme> = datastore.data.map { swIn ->
        LocalSourceDatastoreTheme(
            flag = swIn[SWITCH] ?: false
        )
    }

    companion object {
        /*
        *  config datastore keys
        * */
        val MSG = stringPreferencesKey("msg")
        val SWITCH = booleanPreferencesKey("switch")
    }
}