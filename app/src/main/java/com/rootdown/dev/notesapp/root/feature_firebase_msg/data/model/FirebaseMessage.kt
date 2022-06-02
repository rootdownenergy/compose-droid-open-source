package com.rootdown.dev.notesapp.root.feature_firebase_msg.data.model

import androidx.annotation.Keep

@Keep
data class FirebaseMessage(
    val id: Int,
    val content: String
)