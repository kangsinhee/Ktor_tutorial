package sinhee.kang

import com.google.gson.ExclusionStrategy
import com.google.gson.GsonBuilder
import com.sun.xml.internal.ws.developer.Serialization
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.http.*
import io.ktor.routing.*
import sinhee.kang.config.DatabaseInitializer
import java.text.DateFormat
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder
import java.time.temporal.TemporalAccessor

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

const val DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss"

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    install(DefaultHeaders)
    install(CallLogging)
    install(ContentNegotiation) {
        register(ContentType.Application.Json, GsonConverter())
        gson {
            enableComplexMapKeySerialization()
            setDateFormat(DATE_TIME_FORMAT)
            setPrettyPrinting()

        }
    }
    install(Routing) {
        todo(TodoService())
    }
    DatabaseInitializer.init()
}