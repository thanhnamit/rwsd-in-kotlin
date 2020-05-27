package com.tna.bankanalyzer.domain

interface BankStatementParser {
    fun parseFrom(line: String): BankTransaction
    fun parseLinesFrom(lines: List<String>): List<BankTransaction>
}