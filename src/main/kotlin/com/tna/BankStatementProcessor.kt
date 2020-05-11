package com.tna

import com.tna.domain.BankTransaction
import java.time.LocalDate
import java.time.Month

class BankStatementProcessor(val trans: List<BankTransaction>) {
    fun calculateTotalAmount(): Double {
        return trans.sumByDouble { it.amount }
    }

    fun calculateTotalInMonth(month: Month): Double {
        return trans.filter { it.date.month == month }.sumByDouble { it.amount }
    }

    fun calculateTotalForCategory(category: String): Double {
        return trans.filter { it.description == category }.sumByDouble { it.amount }
    }

    fun findMaxInDateRange(from: LocalDate, to: LocalDate): Double {
        val tran = trans.filter { it.date >= from }.filter { it.date <= to }.maxBy { it.amount }
        return tran?.amount ?: throw Exception("No transaction found in provided range")
    }

    fun getHistogramGroupByMonthAndDesc(): List<BankTransaction> {
        return trans.groupBy { it.date.month }.mapValues { it ->
            it.value.groupBy { trans -> trans.description }.values.flatten()
        }.values.flatten()
    }
}

