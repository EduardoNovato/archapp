package com.eduardo.dev.archapp.ui.screens.dashboard

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.eduardo.dev.archapp.model.Project
import com.eduardo.dev.archapp.model.Task
import com.eduardo.dev.archapp.model.TaskStatus
import com.eduardo.dev.archapp.navegation.Screen
import com.eduardo.dev.archapp.ui.components.ProjectCard
import com.eduardo.dev.archapp.ui.components.SearchBar
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(navController: NavController) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    var searchQuery by remember { mutableStateOf("") }

    // Mock data for demonstration
    val projects = remember {
        listOf(
            Project(
                id = UUID.randomUUID().toString(),
                name = "Modern Residential Complex",
                clientName = "Skyline Developers",
                progress = 0.75f,
                tasks = listOf(
                    Task(title = "Floor plan design", status = TaskStatus.COMPLETED),
                    Task(title = "Structural analysis", status = TaskStatus.COMPLETED),
                    Task(title = "Interior design", status = TaskStatus.IN_PROGRESS),
                    Task(title = "Landscape design", status = TaskStatus.TODO)
                )
            ),
            Project(
                id = UUID.randomUUID().toString(),
                name = "City Center Office Building",
                clientName = "Metro Commercial",
                progress = 0.35f,
                tasks = listOf(
                    Task(title = "Concept design", status = TaskStatus.COMPLETED),
                    Task(title = "Zoning analysis", status = TaskStatus.IN_PROGRESS),
                    Task(title = "MEP coordination", status = TaskStatus.TODO),
                    Task(title = "Facade design", status = TaskStatus.TODO)
                )
            ),
            Project(
                id = UUID.randomUUID().toString(),
                name = "Sustainable Park Pavilion",
                clientName = "Green City Initiative",
                progress = 0.15f,
                tasks = listOf(
                    Task(title = "Site analysis", status = TaskStatus.COMPLETED),
                    Task(title = "Concept sketches", status = TaskStatus.IN_PROGRESS),
                    Task(title = "Material selection", status = TaskStatus.TODO),
                    Task(title = "Energy modeling", status = TaskStatus.TODO)
                )
            )
        )
    }

    val filterProject = projects.filter {
        it.name.contains(searchQuery, ignoreCase = true) ||
        it.clientName.contains(searchQuery, ignoreCase = true)
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Dashboard") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        bottomBar = {
            NavigationBar {
                val items = listOf(
                    "Projects" to Icons.Default.Home,
                    //"Plans" to Icons.Default.Description,
                    //"Budgets" to Icons.Default.MonetizationOn,
                   // "Chat" to Icons.Default.Chat,
                    "Profile" to Icons.Default.Person
                )

                items.forEachIndexed { index, (title, icon) ->
                    NavigationBarItem(
                        icon = { Icon(icon, contentDescription = title) },
                        label = { Text(title) },
                        selected = selectedTabIndex == index,
                        onClick = {
                            selectedTabIndex = index
                            when (index) {
                                0 -> {} // Already on dashboard
                               // 1 -> navController.navigate(Screen.Plans.route)
                                2 -> {} // Navigate to budgets
                                3 -> {} // Navigate to chat
                                //4 -> navController.navigate(Screen.Profile.route)
                            }
                        }
                    )
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* Handle new project creation */ },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Project")
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                SearchBar(
                    query = searchQuery,
                    onQueryChange = { searchQuery = it }
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Active Projects",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )

                LazyColumn(
                    contentPadding = PaddingValues(16.dp)
                ) {
                    itemsIndexed(filterProject) {_, project ->
                        ProjectCard(
                            project = project,
                            onClick = {
                                navController.navigate(Screen.ProjectDetail.createRoute(project.id))
                            }
                        )
                    }
                }
            }
        }
    }
}