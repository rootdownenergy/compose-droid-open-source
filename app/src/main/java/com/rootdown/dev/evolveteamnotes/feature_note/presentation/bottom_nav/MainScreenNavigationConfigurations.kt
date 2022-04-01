package com.rootdown.dev.notesapp.root.feature_note.presentation.bottom_nav

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.rootdown.dev.notesapp.root.feature_note.presentation.BottomNavScreens
import com.rootdown.dev.notesapp.root.feature_note.presentation.auth.LoginScreen
import com.rootdown.dev.notesapp.root.feature_note.presentation.notes_list.NotesScreen
import com.rootdown.dev.notesapp.root.feature_note.presentation.notes_list.NotesEvents

@ExperimentalAnimationApi
@Composable
fun MainScreenNavigationConfigurations(
    navController: NavHostController
) {
    NavHost(navController, startDestination = BottomNavScreens.LoginScreen.route) {

        composable(BottomNavScreens.LoginScreen.route)
        {
            LoginScreen(navController = navController)
        }
        composable(BottomNavScreens.NotesScreen.route)
        {
            NotesScreen(navController = navController)
        }

    }
}