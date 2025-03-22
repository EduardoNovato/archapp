package com.eduardo.dev.archapp.navegation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.eduardo.dev.archapp.ui.screens.auth.LoginScreen
import com.eduardo.dev.archapp.ui.screens.dashboard.DashboardScreen
import com.eduardo.dev.archapp.ui.screens.project.ProjectDetailScreen
import com.eduardo.dev.archapp.ui.screens.profile.ProfileScreen
import com.eduardo.dev.archapp.viewmodel.ui.profile.ThemeViewModel

sealed class Screen(val route: String) {
    data object Login : Screen("login")
    data object Dashboard : Screen("dashboard")
    data object ProjectDetail : Screen("project/{projectId}") {
        fun createRoute(projectId: String) = "project/$projectId"
    }
    data object Profile : Screen("profile")
}

@Composable
fun AppNavHost(navController: NavHostController, themeViewModel: ThemeViewModel) {
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

        composable(Screen.Profile.route) {
            ProfileScreen(navController = navController, themeViewModel = themeViewModel)
        }

        composable(
            route = Screen.ProjectDetail.route,
            arguments = listOf(navArgument("projectId") { type = NavType.StringType })
        ) { backStackEntry ->
            val projectId = backStackEntry.arguments?.getString("projectId") ?: ""
            ProjectDetailScreen(projectId, navController)
        }

    }
}