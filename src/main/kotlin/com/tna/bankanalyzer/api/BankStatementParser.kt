package com.tna.bankanalyzer.api

import com.tna.bankanalyzer.domain.BankTransaction

interface BankStatementParser {
    fun parseFrom(line: String): BankTransaction
    fun parseLinesFrom(lines: List<String>): List<BankTransaction>
}