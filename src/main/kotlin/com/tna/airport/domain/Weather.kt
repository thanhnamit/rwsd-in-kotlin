package com.tna.airport.domain

import com.beust.klaxon.Json

data class Weather(
    @Json(name="Temp") val temperature: Array<String>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Weather

        if (!temperature.contentEquals(other.temperature)) return false

        return true
    }

    override fun hashCode(): Int {
        return temperature.contentHashCode()
    }
}