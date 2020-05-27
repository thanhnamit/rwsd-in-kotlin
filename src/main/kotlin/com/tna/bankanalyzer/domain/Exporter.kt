package com.tna.bankanalyzer.domain

interface Exporter {
    fun export(summaryStatistics: SummaryStatistics): String
}