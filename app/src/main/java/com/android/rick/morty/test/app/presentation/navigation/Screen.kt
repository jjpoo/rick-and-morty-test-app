package com.android.rick.morty.test.app.presentation.navigation

sealed class Screen(val route: String) {
    data object Characters : Screen(route = "characters")

    data object Details : Screen(route = "details/{id}") {
        fun createRoute(id: Int) = "details/$id"
    }
}