package sinhee.kang

import io.ktor.features.*
import io.ktor.util.*
import org.jetbrains.exposed.sql.SortOrder
import sinhee.kang.config.query
import sinhee.kang.entity.Todo
import sinhee.kang.entity.Todos
import sinhee.kang.model.TodoRequest
import sinhee.kang.model.TodoResponse
import java.time.LocalDateTime

@KtorExperimentalAPI
class TodoService {
    suspend fun getAll() = query {
        Todo.all()
            .orderBy(Todos.id to SortOrder.DESC)
            .map(TodoResponse.Companion::of)
            .toList()
    }

    suspend fun getById(id: Int) = query {
        Todo.findById(id)?.run(TodoResponse.Companion::of) ?: throw NotFoundException()
    }


    suspend fun new(content: String) = query {
        Todo.new { this.content = content }
    }

    suspend fun renew(id: Int, req: TodoRequest) = query {
        val todo = Todo.findById(id) ?: throw NotFoundException()
        todo.apply {
            content = req.content
            done = req.done ?: false
            updatedAt = LocalDateTime.now()
        }
    }

    suspend fun delete(id: Int) = query {
        Todo.findById(id)?.delete() ?: throw NotFoundException()
    }
}