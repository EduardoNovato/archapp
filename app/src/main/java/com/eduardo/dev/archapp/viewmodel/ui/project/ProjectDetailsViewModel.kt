package com.eduardo.dev.archapp.viewmodel.ui.project

import android.util.Log
import androidx.lifecycle.ViewModel
import com.eduardo.dev.archapp.model.Document
import com.eduardo.dev.archapp.model.DocumentType
import com.eduardo.dev.archapp.model.Project
import com.eduardo.dev.archapp.model.Task
import com.eduardo.dev.archapp.model.TaskStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.UUID

class ProjectDetailsViewModel : ViewModel() {
    private val _projects = MutableStateFlow(
        listOf(
            Project(
                id = "1",
                name = "Modern Residential Complex",
                clientName = "Skyline Developers",
                progress = 0.75f,
                tasks = listOf(
                    Task(title = "Floor plan design", status = TaskStatus.COMPLETED),
                    Task(title = "Structural analysis", status = TaskStatus.COMPLETED),
                    Task(title = "Interior design", status = TaskStatus.IN_PROGRESS),
                    Task(title = "Landscape design", status = TaskStatus.TODO)
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
            ),
            Project(
                id = "2",
                name = "City Center Office Building",
                clientName = "Metro Commercial",
                progress = 0.35f,
                tasks = listOf(
                    Task(title = "Concept design", status = TaskStatus.COMPLETED),
                    Task(title = "Zoning analysis", status = TaskStatus.IN_PROGRESS),
                    Task(title = "MEP coordination", status = TaskStatus.TODO),
                    Task(title = "Facade design", status = TaskStatus.TODO)
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
            ),
            Project(
                id = "3",
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
    )

    val projects: StateFlow<List<Project>> = _projects

    private val _selectedProject = MutableStateFlow<Project?>(null)
    val selectedProject: StateFlow<Project?> = _selectedProject

    fun selectProject(projectId: String) {
        _selectedProject.value = _projects.value.find { it.id == projectId }
    }
}