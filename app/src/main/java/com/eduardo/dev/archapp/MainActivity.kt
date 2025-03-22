package com.eduardo.dev.archapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.eduardo.dev.archapp.navegation.AppNavHost
import com.eduardo.dev.archapp.ui.theme.ArchappTheme
import com.eduardo.dev.archapp.viewmodel.ui.profile.ThemeViewModel
import kotlin.getValue

class MainActivity : ComponentActivity() {
    val themeViewModel: ThemeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val isDarkModeEnabled by themeViewModel.isDarkTheme.collectAsState()
            ArchappTheme(darkTheme = isDarkModeEnabled) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ArchitectApp(themeViewModel = themeViewModel)
                }
            }
        }
    }
}

@Composable
fun ArchitectApp(themeViewModel: ThemeViewModel) {
    val navController = rememberNavController()
    AppNavHost(navController = navController, themeViewModel = themeViewModel)
}
