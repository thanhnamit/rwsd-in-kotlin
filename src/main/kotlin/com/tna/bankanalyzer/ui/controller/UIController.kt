package com.tna.bankanalyzer.ui.controller

import com.tna.bankanalyzer.BankStatementCSVParser
import com.tna.bankanalyzer.BankStatementProcessor
import com.tna.bankanalyzer.domain.BankTransaction
import com.tna.bankanalyzer.domain.MonthlySummary
import com.tna.bankanalyzer.domain.SummaryStatistics
import tornadofx.Controller
import tornadofx.FX
import java.nio.file.Files
import java.nio.file.Paths

class UIController: Controller() {
    private val RESOURCE = "src/main/resources/"
    private val processor: BankStatementProcessor

    init {
        processor = BankStatementProcessor(BankStatementCSVParser().parseLinesFrom(
            Files.readAllLines(Paths.get(RESOURCE + FX.application.parameters.unnamed[0]))
        ))
    }

    fun getSummaryStatistics(): SummaryStatistics {
        return processor.getSummaryStatistics()
    }

    fun getAllTransactions(): List<BankTransaction> {
        return processor.getAllTransactions()
    }

    fun getMonthlySummary(): List<MonthlySummary> {
        return processor.getMonthlySummary()
    }
}

