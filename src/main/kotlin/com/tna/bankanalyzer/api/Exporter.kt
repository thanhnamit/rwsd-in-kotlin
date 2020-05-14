package com.tna.bankanalyzer.api

import com.tna.bankanalyzer.domain.SummaryStatistics

interface Exporter {
    fun export(summaryStatistics: SummaryStatistics): String
}