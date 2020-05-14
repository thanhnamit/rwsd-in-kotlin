package com.tna.bankanalyzer

import com.tna.bankanalyzer.api.BankStatementParser
import com.tna.bankanalyzer.domain.BankTransaction
import com.tna.bankanalyzer.exception.CSVLineFormatException
import com.tna.bankanalyzer.exception.CSVSyntaxException
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class BankStatementCSVParser : BankStatementParser {
    private val EXPECTED_ATTRIBUTES_LENGTH = 3
    companion object {
        val dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
    }

    /**
     * @throws CSVSyntaxException
     * @throws CSVLineFormatException
     */
    override fun parseFrom(line: String): BankTransaction {
        val cols = line.split(",")
        if (cols.size != EXPECTED_ATTRIBUTES_LENGTH) {
            throw CSVSyntaxException("Invalid line format")
        }

        val notification = BankStatementLineValidator(
            cols[0].trim(),
            cols[2].trim(),
            cols[1].trim()
        ).validate()
        if (notification.hasErrors()) {
            throw CSVLineFormatException("Invalid data format: ${notification.errorMessage()}")
        }

        return BankTransaction(
            LocalDate.parse(cols[0].trim(), dateTimeFormatter),
            cols[1].trim().toDouble(),
            cols[2].trim()
        )
    }

    override fun parseLinesFrom(lines: List<String>): List<BankTransaction> {
        return lines.map { parseFrom(it) }
    }
}