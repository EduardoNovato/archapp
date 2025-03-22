package com.eduardo.dev.archapp.model

import java.util.Date
import java.util.UUID

data class User(
    val id: String = UUID.randomUUID().toString(),
    val email: String,
    val name: String,
    val photoUrl: String? = null,
    val specialization: String = "",
    val darkModeEnabled: Boolean = false,
    val autoSync: Boolean = true,
    val subscriptionPlan: SubscriptionPlan = SubscriptionPlan.FREE,
    val subscriptionRenewalDate: Date? = null
)

enum class SubscriptionPlan {
    FREE, PROFESSIONAL, ENTERPRISE
}

data class Project(
    val id: String = UUID.randomUUID().toString(),
    val name: String = "",
    val clientName: String = "",
    val description: String = "",
    val progress: Float = 0f,
    val startDate: Date = Date(),
    val dueDate: Date? = null,
    val tasks: List<Task> = emptyList(),
    val documents: List<Document> = emptyList(),
    val budget: Budget? = null,
    val team: List<String> = emptyList() // User IDs
)

data class Task(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val description: String = "",
    val status: TaskStatus = TaskStatus.TODO,
    val assignedTo: String? = null, // User ID
    val dueDate: Date? = null,
    val createdAt: Date = Date(),
    val updatedAt: Date = Date()
)

enum class TaskStatus {
    TODO, IN_PROGRESS, COMPLETED
}

data class Document(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val type: DocumentType,
    val url: String,
    val thumbnailUrl: String? = null,
    val createdAt: Date = Date()
)

enum class DocumentType {
    PDF, DWG, PNG, JPG, DOC, XLS, OTHER
}


// Annotation model for plans
data class Annotation(
    val id: String = UUID.randomUUID().toString(),
    val x: Float,
    val y: Float,
    val content: String,
    val type: AnnotationType = AnnotationType.NOTE,
    val createdBy: String, // User ID
    val createdAt: Date = Date()
)

enum class AnnotationType {
    NOTE, MEASUREMENT, DRAWING
}

// Budget model
data class Budget(
    val id: String = UUID.randomUUID().toString(),
    val projectId: String,
    val title: String,
    val description: String = "",
    val items: List<BudgetItem> = emptyList(),
    val totalAmount: Double = 0.0,
    val createdAt: Date = Date(),
    val updatedAt: Date = Date()
)

data class BudgetItem(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val category: String,
    val quantity: Double,
    val unitPrice: Double,
    val totalPrice: Double = quantity * unitPrice
)

// Chat model
data class ChatMessage(
    val id: String = UUID.randomUUID().toString(),
    val projectId: String,
    val senderId: String, // User ID
    val content: String,
    val attachmentUrl: String? = null,
    val attachmentType: DocumentType? = null,
    val referencedDocumentId: String? = null,
    val timestamp: Date = Date(),
    val isRead: Boolean = false
)