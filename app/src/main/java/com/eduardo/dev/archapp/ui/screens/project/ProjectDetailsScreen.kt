package com.eduardo.dev.archapp.ui.screens.project

import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.eduardo.dev.archapp.model.Document
import com.eduardo.dev.archapp.model.Task
import com.eduardo.dev.archapp.model.TaskStatus
import com.eduardo.dev.archapp.ui.Attach_file
import com.eduardo.dev.archapp.ui.Chat
import com.eduardo.dev.archapp.ui.Description
import com.eduardo.dev.archapp.ui.Drive_folder_upload
import com.eduardo.dev.archapp.ui.theme.Blue500
import com.eduardo.dev.archapp.ui.theme.Gray200
import com.eduardo.dev.archapp.ui.theme.Gray300
import com.eduardo.dev.archapp.ui.theme.SuccessGreen
import com.eduardo.dev.archapp.ui.theme.WarningYellow
import com.eduardo.dev.archapp.viewmodel.ui.project.ProjectDetailsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProjectDetailScreen(
    projectId: String,
    navController: NavController,
    projectViewModel: ProjectDetailsViewModel = viewModel()
) {
    LaunchedEffect(projectId) {
        projectViewModel.selectProject(projectId)
    }
    val project by projectViewModel.selectedProject.collectAsState()

    if (project == null) {
        Box(
            modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
        ) {
            Text("Project not found or loading...")
        }
        return
    }

    // A partir de aquí, project es NO NULLABLE (por el return anterior)
    val nonNullProject = project!!

    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val tabTitles = listOf("Tasks", "Documents", "Budget")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(nonNullProject.name) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        //navController.navigate(Screen.Chat.createRoute(projectId))
                    }) {
                        Icon(
                            imageVector = Chat, contentDescription = "Chat"
                        )
                    }
                    IconButton(onClick = { /* Open menu */ }) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "More options"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState()) // Scroll global aquí
        ) {
            // Project header with progress
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = "Client: ${nonNullProject.clientName ?: "Unknown"}",
                                style = MaterialTheme.typography.titleMedium
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = nonNullProject.description ?: "",
                                style = MaterialTheme.typography.bodyMedium,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Progress: ${(nonNullProject.progress * 100).toInt()}%",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.width(16.dp))

                        LinearProgressIndicator(
                            progress = { nonNullProject.progress },
                            modifier = Modifier
                                .weight(1f)
                                .height(8.dp)
                                .clip(RoundedCornerShape(4.dp)),
                            color = when {
                                nonNullProject.progress < 0.3f -> WarningYellow
                                nonNullProject.progress < 0.7f -> Blue500
                                else -> SuccessGreen
                            },
                            trackColor = Gray200
                        )
                    }
                }
            }

            // Kanban board section
            Text(
                text = "Kanban Board",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )

            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                val tasks = nonNullProject.tasks ?: emptyList()
                val todoTasks = tasks.filter { it.status == TaskStatus.TODO }
                val inProgressTasks = tasks.filter { it.status == TaskStatus.IN_PROGRESS }
                val completedTasks = tasks.filter { it.status == TaskStatus.COMPLETED }

                item {
                    KanbanColumn(
                        title = "To Do",
                        tasks = todoTasks,
                        color = WarningYellow,
                        onTaskClick = { /* Handle task click */ }
                    )
                }

                item {
                    KanbanColumn(
                        title = "In Progress",
                        tasks = inProgressTasks,
                        color = Blue500,
                        onTaskClick = { /* Handle task click */ }
                    )
                }

                item {
                    KanbanColumn(
                        title = "Completed",
                        tasks = completedTasks,
                        color = SuccessGreen,
                        onTaskClick = { /* Handle task click */ }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Tabs for Tasks, Documents, Budget
            TabRow(selectedTabIndex = selectedTabIndex) {
                tabTitles.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index },
                        text = { Text(title) }
                    )
                }
            }

            // Contenido de las pestañas (sin scroll interno)
            when (selectedTabIndex) {
                0 -> TasksTabContent(nonNullProject.tasks ?: emptyList())
                1 -> DocumentsTabContent(
                    documents = nonNullProject.documents ?: emptyList(),
                    onDocumentClick = { document ->
                        //navController.navigate(Screen.PlanDetail.createRoute(document.id))
                    }
                )

                2 -> BudgetTabContent(
                    projectId = projectId, navController = navController
                )
            }
        }
    }
}

@Composable
fun TasksTabContent(tasks: List<Task>) {
    Column {
        // Mostrar cada tarea en la lista
        tasks.forEach { task ->
            TaskListItem(task = task)
        }

        // Si no hay tareas, mostrar un mensaje
        if (tasks.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No tasks available",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
fun DocumentsTabContent(
    documents: List<Document>,
    onDocumentClick: (Document) -> Unit
) {
    Column {
        // Mostrar cada documento en la lista
        documents.forEach { document ->
            DocumentListItem(document = document, onClick = { onDocumentClick(document) })
        }

        // Si no hay documentos, mostrar un mensaje
        if (documents.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No documents available",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
fun BudgetTabContent(
    projectId: String, navController: NavController
) {
    Column {
        Text(
            text = "No budget created yet", style = MaterialTheme.typography.titleMedium
        )
        FloatingActionButton(
            onClick = {
                //navController.navigate(Screen.BudgetCreation.createRoute(projectId))
            },
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ) {
            Icon(Icons.Default.Add, contentDescription = "Create Budget")
        }
    }
}

@Composable
fun KanbanColumn(
    title: String, tasks: List<Task>, color: Color, onTaskClick: (Task) -> Unit
) {
    Card(
        modifier = Modifier
            .width(280.dp)
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(color)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = "${tasks.size}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            HorizontalDivider(color = Gray300)

            LazyColumn(
                modifier = Modifier.height(300.dp)
            ) {
                items(tasks.size) { task ->
                    TaskCard(task = tasks[task], onClick = { onTaskClick(tasks[task]) })
                }
            }
        }
    }
}

@Composable
fun TaskCard(
    task: Task, onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Text(
                text = task.title,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold
            )

            if (task.description.isNotEmpty()) {
                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = task.description,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }

            if (task.dueDate != null) {
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Due: ${task.dueDate}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
fun TasksTab(tasks: List<Task>) {
    Column {
        tasks.forEach { task ->
            TaskListItem(task = task)
        }
    }
}

@Composable
fun TaskListItem(task: Task) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(16.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(
                        when (task.status) {
                            TaskStatus.TODO -> WarningYellow
                            TaskStatus.IN_PROGRESS -> Blue500
                            TaskStatus.COMPLETED -> SuccessGreen
                        }
                    )
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = task.title, style = MaterialTheme.typography.titleMedium
                )

                if (task.description.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = task.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            IconButton(onClick = { /* Edit task */ }) {
                Icon(
                    imageVector = Icons.Default.Edit, contentDescription = "Edit Task"
                )
            }
        }
    }
}

// Document Tab Content
@Composable
fun DocumentsTab(
    documents: List<Document>, onDocumentClick: (Document) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Project Documents",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.weight(1f)
            )

            IconButton(onClick = { /* Upload document */ }) {
                Icon(
                    imageVector = Drive_folder_upload, contentDescription = "Upload Document"
                )
            }
        }

        Column {
            documents.forEach { document ->
                DocumentListItem(document = document, onClick = { onDocumentClick(document) })
            }
        }
    }
}

@Composable
fun DocumentListItem(
    document: Document, onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Description,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = document.name, style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Type: ${document.type.name}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            IconButton(onClick = { /* Share document */ }) {
                Icon(
                    imageVector = Attach_file, contentDescription = "Share Document"
                )
            }
        }
    }
}

@Composable
fun BudgetTab(
    projectId: String, navController: NavController
) {
    Column {
        Text(
            text = "No budget created yet", style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        FloatingActionButton(
            onClick = {
                //navController.navigate(Screen.BudgetCreation.createRoute(projectId))
            },
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ) {
            Icon(Icons.Default.Add, contentDescription = "Create Budget")
        }
    }
}

@Preview
@Composable
fun TaskListItemsPreview() {
    TaskListItem(
        task = Task(
            title = "Task 1",
            description = "This is a task description",
            status = TaskStatus.TODO
        )
    )
}

@Preview
@Composable
fun TasksTabPreview() {
    TasksTab(
        tasks = listOf(
            Task(
                title = "Task 1",
                description = "This is a task description",
                status = TaskStatus.TODO
            ),
            Task(
                title = "Task 2",
                description = "This is a task description",
                status = TaskStatus.IN_PROGRESS
            ),
            Task(
                title = "Task 3",
                description = "This is a task description",
                status = TaskStatus.COMPLETED
            )
        )
    )
}
