package com.tna.bankanalyzer.domain

import com.tna.bankanalyzer.domain.SummaryStatistics

interface Exporter {
    fun export(summaryStatistics: SummaryStatistics): String
}