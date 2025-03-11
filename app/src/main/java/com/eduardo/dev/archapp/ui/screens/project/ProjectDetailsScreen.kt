package com.eduardo.dev.archapp.ui.screens.project

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.eduardo.dev.archapp.model.Document
import com.eduardo.dev.archapp.model.DocumentType
import com.eduardo.dev.archapp.model.Project
import com.eduardo.dev.archapp.model.Task
import com.eduardo.dev.archapp.model.TaskStatus
import com.eduardo.dev.archapp.navegation.Screen
import com.eduardo.dev.archapp.ui.theme.Blue500
import com.eduardo.dev.archapp.ui.theme.Gray200
import com.eduardo.dev.archapp.ui.theme.Gray300
import com.eduardo.dev.archapp.ui.theme.SuccessGreen
import com.eduardo.dev.archapp.ui.theme.WarningYellow
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProjectDetailScreen(
    projectId: String,
    navController: NavController
) {
    // In a real app, fetch project details based on projectId
    val project = remember {
        Project(
            id = projectId,
            name = "Modern Residential Complex",
            clientName = "Skyline Developers",
            description = "A luxury residential complex with 50 units, featuring modern architecture and sustainable design elements.",
            progress = 0.75f,
            tasks = listOf(
                Task(
                    title = "Floor plan design",
                    description = "Create detailed floor plans for all unit types",
                    status = TaskStatus.COMPLETED
                ),
                Task(
                    title = "Structural analysis",
                    description = "Perform structural calculations and analysis",
                    status = TaskStatus.COMPLETED
                ),
                Task(
                    title = "Interior design",
                    description = "Develop interior design concepts for common areas",
                    status = TaskStatus.IN_PROGRESS
                ),
                Task(
                    title = "Landscape design",
                    description = "Design outdoor spaces and landscaping",
                    status = TaskStatus.TODO
                ),
                Task(
                    title = "MEP coordination",
                    description = "Coordinate mechanical, electrical, and plumbing systems",
                    status = TaskStatus.TODO
                )
            ),
            documents = listOf(
                Document(
                    id = UUID.randomUUID().toString(),
                    name = "Site Plan.pdf",
                    type = DocumentType.PDF,
                    url = "https://example.com/site-plan.pdf",
                    thumbnailUrl = "https://via.placeholder.com/100x100"
                ),
                Document(
                    id = UUID.randomUUID().toString(),
                    name = "Floor Plans.dwg",
                    type = DocumentType.DWG,
                    url = "https://example.com/floor-plans.dwg",
                    thumbnailUrl = "https://via.placeholder.com/100x100"
                ),
                Document(
                    id = UUID.randomUUID().toString(),
                    name = "Elevation Render.png",
                    type = DocumentType.PNG,
                    url = "https://example.com/elevation.png",
                    thumbnailUrl = "https://via.placeholder.com/100x100"
                )
            )
        )
    }

    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val tabTitles = listOf("Tasks", "Documents", "Budget")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(project.name) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        navController.navigate(Screen.Chat.createRoute(projectId))
                    }) {
                        Icon(
                            imageVector = Icons.Default.Chat,
                            contentDescription = "Chat"
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
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* Add new task/document/budget item based on selected tab */ },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
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
                                text = "Client: ${project.clientName}",
                                style = MaterialTheme.typography.titleMedium
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = project.description,
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
                            text = "Progress: ${(project.progress * 100).toInt()}%",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.width(16.dp))

                        LinearProgressIndicator(
                            strokeCap = { project.progress },
                            modifier = Modifier
                                .weight(1f)
                                .height(8.dp)
                                .clip(RoundedCornerShape(4.dp)),
                            color = when {
                                project.progress < 0.3f -> WarningYellow
                                project.progress < 0.7f -> Blue500
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
                val todoTasks = project.tasks.filter { it.status == TaskStatus.TODO }
                val inProgressTasks = project.tasks.filter { it.status == TaskStatus.IN_PROGRESS }
                val completedTasks = project.tasks.filter { it.status == TaskStatus.COMPLETED }

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

            when (selectedTabIndex) {
                0 -> TasksTab(project.tasks)
                1 -> DocumentsTab(
                    documents = project.documents,
                    onDocumentClick = { document ->
                        navController.navigate(Screen.PlanDetail.createRoute(document.id))
                    }
                )
                2 -> BudgetTab(
                    projectId = projectId,
                    navController = navController
                )
            }
        }
    }
}

@Composable
fun KanbanColumn(
    title: String,
    tasks: List<Task>,
    color: Color,
    onTaskClick: (Task) -> Unit
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

            Divider(color = Gray300)

            LazyColumn(
                modifier = Modifier.height(300.dp)
            ) {
                items(tasks.size) { task ->
                    TaskCard(
                        task = tasks[task],
                        onClick = { onTaskClick(tasks[task]) }
                    )
                }
            }
        }
    }
}

@Composable
fun TaskCard(
    task: Task,
    onClick: () -> Unit
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
    LazyColumn(
        contentPadding = PaddingValues(16.dp)
    ) {
        items(tasks) { task ->
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
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
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
                    text = task.title,
                    style = MaterialTheme.typography.titleMedium
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
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Task"
                )
            }
        }
    }
}

@Composable
fun DocumentsTab(
    documents: List<Document>,
    onDocumentClick: (Document) -> Unit
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
                    imageVector = Icons.Default.Upload,
                    contentDescription = "Upload Document"
                )
            }
        }

        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(documents) { document ->
                DocumentListItem(
                    document = document,
                    onClick = { onDocumentClick(document) }
                )
            }
        }
    }
}

@Composable
fun DocumentListItem(
    document: Document,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Description,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = document.name,
                    style = MaterialTheme.typography.titleMedium
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
                    imageVector = Icons.Default.AttachFile,
                    contentDescription = "Share Document"
                )
            }
        }
    }
}

@Composable
fun BudgetTab(
    projectId: String,
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "No budget created yet",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        FloatingActionButton(
            onClick = {
                navController.navigate(Screen.BudgetCreation.createRoute(projectId))
            },
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ) {
            Icon(Icons.Default.Add, contentDescription = "Create Budget")
        }
    }
}