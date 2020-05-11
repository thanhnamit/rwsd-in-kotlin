package com.tna

import com.tna.api.BankStatementParser
import java.nio.file.Files
import java.nio.file.Paths
import java.time.Month

class BankTransactionAnalyzer() {
    private val RESOURCE = "src/main/resources/"

    fun analyze(file: String, parser: BankStatementParser) {
        val path = Paths.get(RESOURCE + file)
        val processor = BankStatementProcessor(parser.parseLinesFrom(Files.readAllLines(path)))
        collectSummary(processor)
    }

    private fun collectSummary(processor: BankStatementProcessor) {
        println("The total for all transactions is ${processor.calculateTotalAmount()}")
        println("The total for all transactions in Jan is ${processor.calculateTotalInMonth(Month.JANUARY)}")
        println("The total salary received is ${processor.calculateTotalForCategory("Salary")}")
    }
}




