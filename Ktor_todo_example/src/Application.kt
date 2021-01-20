package sinhee.kang

import io.ktor.application.*
import io.ktor.features.*
import com.fasterxml.jackson.databind.*
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import io.ktor.jackson.*
import io.ktor.routing.*
import sinhee.kang.config.DatabaseInitializer
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

const val DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss"

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    install(DefaultHeaders)
    install(CallLogging)
    install(ContentNegotiation) {
        jackson {
            enable(SerializationFeature.INDENT_OUTPUT)
            registerModule(JavaTimeModule().apply {
                addSerializer(
                    LocalDateTimeSerializer(
                    DateTimeFormatter.ofPattern(DATE_TIME_FORMAT))
                )
                addDeserializer(
                    LocalDateTime::class.java,
                    LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT))
                )
            })
        }
    }
    install(Routing) {
        todo(TodoService())
    }
    DatabaseInitializer.init()
}