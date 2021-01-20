package sinhee.kang.model

import java.time.LocalDateTime

class TodoRequest (
    val content: String,
    val done: Boolean?,
    val createdAt: LocalDateTime?,
    val updatedAt: LocalDateTime?
)