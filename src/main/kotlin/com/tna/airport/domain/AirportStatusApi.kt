package com.tna.airport.domain

interface AirportStatusApi {
    suspend fun getOne(code: String): Airport?
}