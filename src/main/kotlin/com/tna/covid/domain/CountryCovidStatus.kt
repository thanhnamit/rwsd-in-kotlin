package com.tna.covid.domain

import com.beust.klaxon.Json

data class CountryCovidStatus(
    @Json(name = "Country") val country: String,
    @Json(name = "Confirmed") val confirmed: Int,
    @Json(name = "Deaths") val deaths: Int,
    @Json(name = "Active") val active: Int,
    @Json(name = "Date") val date: String
)