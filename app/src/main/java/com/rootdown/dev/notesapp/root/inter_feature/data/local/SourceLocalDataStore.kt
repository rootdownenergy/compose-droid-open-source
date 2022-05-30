package com.rootdown.dev.notesapp.root.inter_feature.data.local

import com.squareup.moshi.Moshi
import javax.inject.Inject

class SourceLocalDataStore @Inject constructor(
    private val preferencesManager: LocalSourceImpl,
    private val moshi : Moshi
){
    suspend fun saveMsg() {

    }
}