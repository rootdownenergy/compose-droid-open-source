package com.rootdown.dev.notesapp.root.feature_note.presentation

import com.rootdown.dev.notesapp.root.feature_note.presentation.util.Screen

sealed class BottomNavScreens(val route: String) {
    object NotesScreen: BottomNavScreens("notes_screen")
    object LoginScreen: BottomNavScreens("login_screen")
}