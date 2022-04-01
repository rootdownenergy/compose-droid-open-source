package com.rootdown.dev.notesapp.root.feature_note.presentation.bottom_nav

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.rootdown.dev.notesapp.root.NoteApp
import com.rootdown.dev.notesapp.root.feature_note.presentation.BottomNavScreens
import com.rootdown.dev.notesapp.root.feature_note.presentation.util.NavigationItem
import com.rootdown.dev.notesapp.root.feature_note.presentation.util.Screen

@ExperimentalAnimationApi
@Composable
fun MainScreen(){
    val navController = rememberNavController()
    val bottomNavigationItems = listOf(
        NavigationItem.PairingScreen
    )
    Scaffold(
        bottomBar = {
            NoteAppBottomNavigation(navController, bottomNavigationItems)
        }
    ) {
        MainScreenNavigationConfigurations(navController)
    }
}