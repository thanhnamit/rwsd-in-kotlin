package com.tna.covid

import com.tna.covid.domain.CountryCovidStatus
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test

class CovidStatusClientTest {
    @Test
    fun `should return airport async`() = runBlocking {
        val client = mockk<CovidStatusClient>()
        coEvery { client.getTotalByCountrySlug("abc") } returns CountryCovidStatus("abc", 10, 10, 10, "2013-02-03")
        client.getTotalByCountrySlug("abc")
        coVerify { client.getTotalByCountrySlug("abc") }
    }
}