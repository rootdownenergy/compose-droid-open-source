package com.rootdown.dev.notesapp.root.inter_feature.presentation.util

import com.rootdown.dev.notesapp.R

sealed class NavigationItem(var route: String, var icon: Int, var title: String) {
    object PairingScreen: NavigationItem(Screen.PairingScreen.route, R.drawable.ic_baseline_data_exploration_24, "Pong")
}