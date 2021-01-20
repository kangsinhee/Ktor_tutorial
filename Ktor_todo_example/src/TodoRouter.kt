package sinhee.kang

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.util.*
import sinhee.kang.entity.Todo
import sinhee.kang.model.TodoRequest
import java.time.LocalDateTime

@KtorExperimentalAPI
fun Routing.todo(service: TodoService) { // 사용자 정의 확장 함수 컨트롤러 부분에 해당?
    route("todos") { //URL 접두사
        get {
            val gson = GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create()
            call.respondText(gson.toJson(service.getAll()), contentType = ContentType.Application.Json)
        }

        get("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
                ?: throw BadRequestException("Parameter id is null")
            val content = service.getById(id)
            println(content.createdAt)

            call.respond(content)
        }

        post {
            val  body = call.receive<TodoRequest>()
            val new: Todo = service.new(body.content)
            println(new.createdAt)
            call.response.status(HttpStatusCode.Created)
        }

        patch("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
                ?: throw BadRequestException("Parameter id is null")
            val body = call.receive<TodoRequest>()
            service.renew(id, body)
            call.response.status(HttpStatusCode.OK)
        }

        delete("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
                ?: throw BadRequestException("Parameter id is null")
            service.delete(id)
            call.response.status(HttpStatusCode.OK)
        }
    }
}