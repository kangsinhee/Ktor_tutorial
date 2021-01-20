package sinhee.kang

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.util.*
import sinhee.kang.model.TodoRequest

@KtorExperimentalAPI
fun Routing.todo(service: TodoService) { // 사용자 정의 확장 함수 컨트롤러 부분에 해당?
    route("todos") { //URL 접두사
        get {
            call.respond(service.getAll())
        }

        get("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
                ?: throw BadRequestException("Parameter id is null")
            call.respond(service.getById(id))
        }

        post {
            val body = call.receive<TodoRequest>()
            service.new(body.content)
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