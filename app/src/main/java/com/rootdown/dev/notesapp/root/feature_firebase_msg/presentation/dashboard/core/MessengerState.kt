package com.rootdown.dev.notesapp.root.feature_firebase_msg.presentation.dashboard.core

import com.rootdown.dev.notesapp.root.inter_feature.data.model.LocalSourceDatastoreMsg

data class MessengerState (
    val msgLs: List<LocalSourceDatastoreMsg> = emptyList()
)