package com.tna.airport.domain

import com.beust.klaxon.Json

data class Airport (
    @Json(name = "IATA") val code: String,
    @Json(name = "Name") val name: String,
    @Json(name = "Delay") val delay: Boolean,
    @Json(name = "Weather") val weather: Weather
)