package com.rootdown.dev.notesapp.root.feature_note.presentation.bottom_nav

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.rootdown.dev.notesapp.root.inter_feature.presentation.util.NavigationItem

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