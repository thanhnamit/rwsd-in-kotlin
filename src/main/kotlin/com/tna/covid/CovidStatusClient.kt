package com.tna.covid

import com.beust.klaxon.Klaxon
import com.tna.covid.domain.CountryCovidStatus
import com.tna.covid.domain.CovidStatusApi
import java.net.URL

class CovidStatusClient: CovidStatusApi {
    private val statusUrl = "https://api.covid19api.com/total/country/"

    override suspend fun getTotalByCountrySlug(code: String): CountryCovidStatus? {
        return Klaxon().parseArray<CountryCovidStatus>(URL(statusUrl + code).readText())?.lastOrNull()
    }
}