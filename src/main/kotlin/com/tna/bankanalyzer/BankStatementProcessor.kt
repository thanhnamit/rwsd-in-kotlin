package com.tna.bankanalyzer

import com.tna.bankanalyzer.domain.BankTransaction
import com.tna.bankanalyzer.domain.MonthlySummary
import com.tna.bankanalyzer.domain.SummaryStatistics
import java.time.LocalDate
import java.time.Month
import kotlin.math.absoluteValue

class BankStatementProcessor(val trans: List<BankTransaction>) {
    fun calculateTotalAmount(): Double {
        return trans.sumByDouble { it.amount }
    }

    fun findMax(): Double {
        return trans.maxBy { it.amount }?.amount ?: throw Exception("No transaction")
    }

    fun findMin(): Double {
        return trans.minBy { it.amount }?.amount ?: throw Exception("No transaction")
    }

    fun findAverage(): Double {
        return calculateTotalAmount() / trans.size
    }

    fun getSummaryStatistics(): SummaryStatistics {
        return SummaryStatistics(calculateTotalAmount(), findMax(), findMin(), findAverage())
    }

    fun calculateTotalInMonth(month: Month): Double {
        return trans.filter { it.date.month == month }.sumByDouble { it.amount }
    }

    fun calculateTotalForCategory(category: String): Double {
        return trans.filter { it.description == category }.sumByDouble { it.amount }
    }

    fun findTransactions(test: (BankTransaction) -> Boolean): List<BankTransaction> {
        return trans.filter(test)
    }

    fun findMaxInDateRange(from: LocalDate, to: LocalDate): Double {
        val tran = trans.filter { it.date >= from }.filter { it.date <= to }.maxBy { it.amount }
        return tran?.amount ?: throw Exception("No transaction found in provided range")
    }

    fun getHistogramGroupByMonthAndDesc(): List<BankTransaction> {
        return trans.groupBy { it.date.month }
                    .mapValues { it.value.groupBy { t -> t.description }.values.flatten() }
                    .values.flatten()
    }

    fun getAllTransactions(): List<BankTransaction> {
        return trans
    }

    fun getMonthlySummary(): List<MonthlySummary> {
        return trans.groupBy { it.date.month }.map { entry ->
            MonthlySummary(entry.key.name,
                entry.value.filter { it.amount >= 0 }.sumByDouble { it.amount }.absoluteValue,
                entry.value.filter { it.amount < 0 }.sumByDouble { it.amount }.absoluteValue
            )
        }
    }
}

