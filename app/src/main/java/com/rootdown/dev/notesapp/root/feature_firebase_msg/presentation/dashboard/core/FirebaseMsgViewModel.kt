package com.rootdown.dev.notesapp.root.feature_firebase_msg.presentation.dashboard.core


import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.database.FirebaseDatabase
import com.rootdown.dev.notesapp.root.inter_feature.data.remote.DatastoreRepoImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FirebaseMsgViewModel @Inject constructor(
    private val fireDb: FirebaseDatabase,
    private val dataStoreRepo: DatastoreRepoImpl
) : ViewModel() {

    private val _state = mutableStateOf(MessengerState())
    val state: State<MessengerState> = _state


}