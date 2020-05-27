package com.tna.airport

import com.tna.airport.domain.Airport
import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

fun main() = runBlocking {
    val format = "%-10s%-20s%-10s"
    println(String.format(format, "Code", "Temperature", "Delay"))

    val client = AirportStatusClient()
    val time = measureTimeMillis {
        val airportCodes = listOf<String>("LAX", "SF-", "PDX", "SEA")
        val airportStatuses: List<Deferred<Airport?>> = airportCodes.map { code ->
            async(Dispatchers.IO + SupervisorJob()) {
                client.getOne(code)
            }
        }

        for (status in airportStatuses) {
            try {
                val info = status.await()
                println(String.format(format, info?.code.toString(), info?.weather?.temperature?.get(0)?.toString(), info?.delay))
            } catch (ex: Exception) {
                println("Error: ${ex.message?.substring(0..30)}")
            }
        }
        println("Main thread: ${Thread.currentThread()}")
    }

    println("Time taken: ${time} ms")
}