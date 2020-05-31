package com.tna.covid.domain

interface CovidStatusApi {
    fun getTotalByCountrySlug(code: String): CountryCovidStatus?
}