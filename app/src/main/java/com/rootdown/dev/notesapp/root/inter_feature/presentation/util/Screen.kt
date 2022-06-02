package com.rootdown.dev.notesapp.root.inter_feature.presentation.util

sealed class Screen(val route: String){
    object NotesScreen: Screen("notes_screen")
    object AddEditNotesScreen: Screen("add_edit_notes_screen")
    object LoginScreen: Screen("login_screen")
    object PairingScreen: Screen("cdm_screen")
    object FirebaseMsgScreen: Screen("firebase_messaging")
}
