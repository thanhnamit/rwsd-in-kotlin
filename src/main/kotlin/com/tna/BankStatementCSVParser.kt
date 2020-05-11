package com.tna

import com.tna.api.BankStatementParser
import com.tna.domain.BankTransaction
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class BankStatementCSVParser : BankStatementParser {
    private val dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")

    override fun parseFrom(line: String): BankTransaction {
        val cols = line.split(",")
        val date = LocalDate.parse(cols[0].trim(), dateTimeFormatter)
        val amount = cols[1].trim().toDouble()
        val des = cols[2].trim()
        return BankTransaction(date, amount, des)
    }

    override fun parseLinesFrom(lines: List<String>): List<BankTransaction> {
        return lines.map { parseFrom(it) }
    }
}