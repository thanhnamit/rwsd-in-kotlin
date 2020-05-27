package com.tna.covid.domain

interface CovidStatusApi {
    suspend fun getTotalByCountrySlug(code: String): CountryCovidStatus?
}