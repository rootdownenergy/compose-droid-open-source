package com.rootdown.dev.notesapp.root.feature_note.presentation

sealed class BottomNavScreens(val route: String) {
    object NotesScreen: BottomNavScreens("notes_screen")
    object LoginScreen: BottomNavScreens("login_screen")
}