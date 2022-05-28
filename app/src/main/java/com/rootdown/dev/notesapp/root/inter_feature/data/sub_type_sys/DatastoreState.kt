package com.rootdown.dev.notesapp.root.inter_feature.data.sub_type_sys


import com.rootdown.dev.notesapp.root.inter_feature.data.model.LocalSourceDatastoreMsg
import com.rootdown.dev.notesapp.root.inter_feature.data.model.LocalSourceDatastoreTheme

sealed class DatastoreState {
    data class ThemeSwitch(val switch: LocalSourceDatastoreTheme): DatastoreState()
    data class MsgSaver(val data: LocalSourceDatastoreMsg): DatastoreState()
}
