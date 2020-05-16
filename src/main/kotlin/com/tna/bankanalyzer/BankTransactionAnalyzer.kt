package com.tna.bankanalyzer

import com.tna.bankanalyzer.domain.BankStatementParser
import java.nio.file.Files
import java.nio.file.Paths
import java.time.Month

/**
 * Console analyzer
 */
class BankTransactionAnalyzer() {
    private val RESOURCE = "src/main/resources/"

    fun analyze(file: String, parser: BankStatementParser) {
        val path = Paths.get(RESOURCE + file)
        // inefficient readAllLines for more than 2GB file, should use File("file").forEachLine {}
        val processor =
            BankStatementProcessor(parser.parseLinesFrom(Files.readAllLines(path)))
        collectSummary(processor)
    }

    private fun collectSummary(processor: BankStatementProcessor) {
        println("The total for all transactions is ${processor.calculateTotalAmount()}")
        println("The total for all transactions in Jan is ${processor.calculateTotalInMonth(Month.JANUARY)}")
        println("The total salary received is ${processor.calculateTotalForCategory("Salary")}")
    }
}




