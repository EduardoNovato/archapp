package com.eduardo.dev.archapp.navegation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.eduardo.dev.archapp.ui.screens.auth.LoginScreen
import com.eduardo.dev.archapp.ui.screens.dashboard.DashboardScreen

sealed class Screen(val route: String) {
    data object Login : Screen("login")
    data object Dashboard : Screen("dashboard")
}

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
        composable(Screen.Login.route) {
            LoginScreen(navController = navController)
        }

        composable(Screen.Dashboard.route) {
            DashboardScreen(navController = navController)
        }
    }
}