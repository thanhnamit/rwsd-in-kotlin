package com.tna.bankanalyzer.exporter

import com.beust.klaxon.Klaxon
import com.tna.bankanalyzer.api.Exporter
import com.tna.bankanalyzer.domain.SummaryStatistics

class JsonExporter: Exporter {
    override fun export(summaryStatistics: SummaryStatistics): String {
        return Klaxon().toJsonString(summaryStatistics)
    }
}