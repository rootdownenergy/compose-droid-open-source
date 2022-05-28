package com.rootdown.dev.notesapp.root.inter_feature.data.local

import androidx.lifecycle.LiveData
import com.rootdown.dev.notesapp.root.inter_feature.data.model.LocalSourceDatastoreMsg
import com.rootdown.dev.notesapp.root.inter_feature.data.model.LocalSourceDatastoreTheme
import kotlinx.coroutines.flow.Flow

interface LocalSource {
    suspend fun saveMsg(data: LocalSourceDatastoreMsg)
    suspend fun getMsg(): Flow<LocalSourceDatastoreMsg>
    suspend fun switchTheme(switch: LocalSourceDatastoreTheme)
    suspend fun getTheme(): Flow<LocalSourceDatastoreTheme>
}