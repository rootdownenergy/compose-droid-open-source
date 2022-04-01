package com.rootdown.dev.notesapp.root.feature_note.presentation.util

import com.rootdown.dev.notesapp.R

sealed class NavigationItem(var route: String, var icon: Int, var title: String) {
    object PairingScreen: NavigationItem("lte", R.drawable.ic_baseline_data_exploration_24, "Pong")
}