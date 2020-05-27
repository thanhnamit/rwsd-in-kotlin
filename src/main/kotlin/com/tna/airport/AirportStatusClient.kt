package com.tna.airport

import com.beust.klaxon.Klaxon
import com.tna.airport.domain.Airport
import com.tna.airport.domain.AirportStatusApi
import java.net.URL

class AirportStatusClient: AirportStatusApi {
    private val statusUrl = "https://soa.smext.faa.gov/asws/api/airport/status/"

    override suspend fun getOne(code: String): Airport? {
        println("${Thread.currentThread()}")
        return Klaxon().parse<Airport>(URL(statusUrl + code).readText())
    }
}