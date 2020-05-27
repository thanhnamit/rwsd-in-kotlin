package com.tna.covid

import com.tna.covid.domain.CountryCovidStatus
import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

fun main() = runBlocking {
    val format = "%-30s%-20s%-20s%-20s"
    println(String.format(format, "Country", "Confirmed", "Deaths", "Active"))

    val client = CovidStatusClient()
    val countryCodes = listOf<String>("united-states", "russia", "australia", "singapore", "thai-")
    val covidStatuses: List<Deferred<CountryCovidStatus?>> = countryCodes.map { code ->
        async(Dispatchers.IO + SupervisorJob()) {
            client.getTotalByCountrySlug(code)
        }
    }

    for (status in covidStatuses) {
        try {
            val info = status.await()
            println(String.format(format, info?.country, info?.confirmed, info?.deaths, info?.active))
        } catch (ex: Exception) {
            println("Error: ${ex.message?.substring(0..30)}")
        }
    }
}