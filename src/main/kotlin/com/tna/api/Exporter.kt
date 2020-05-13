package com.tna.api

import com.tna.domain.SummaryStatistics

interface Exporter {
    fun export(summaryStatistics: SummaryStatistics): String
}