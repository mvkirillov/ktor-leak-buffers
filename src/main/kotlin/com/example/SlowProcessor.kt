package com.example

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectIndexed
import java.nio.ByteBuffer
import java.time.Duration

object SlowProcessor {
    suspend fun process(flow: Flow<ByteBuffer>) {
        flow.collectIndexed { index, value ->
            kotlinx.coroutines.time.delay(Duration.ofMinutes(1))
            println("collected $index buffer ${value.asCharBuffer()}")
        }
    }
}