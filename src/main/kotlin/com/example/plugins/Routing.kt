package com.example.plugins

import com.example.SlowProcessor
import io.ktor.server.routing.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.request.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.nio.ByteBuffer

fun Application.configureRouting() {

    // Starting point for a Ktor app:
    routing {
        get("/") {
            call.respondText("Hello World!")
        }
        post("/upload") {
            val flow = call.receiveFlow()
            SlowProcessor.process(flow)
            call.respondText("success process")
        }
    }
    routing {
    }
}


fun ApplicationCall.receiveFlow(): Flow<ByteBuffer> = flow<ByteBuffer> {

    val channel = receiveChannel()
    try {
        while (true) {
            val buffer = ByteBuffer.allocate(4096)
            if (channel.readAvailable(buffer) == -1) {
                break
            }
            buffer.flip()
            emit(buffer)
        }
    } catch (e: Exception) {
        throw e
    }
}