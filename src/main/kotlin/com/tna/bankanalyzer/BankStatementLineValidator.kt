package com.tna.bankanalyzer

import com.tna.bankanalyzer.domain.Notification
import java.time.LocalDate
import java.time.format.DateTimeParseException

class BankStatementLineValidator(val date: String, val desc: String, val amount: String) {
    fun validate(): Notification {
        val notification = Notification()
        if (desc.length > 100) {
            notification.addError("The description is too long")
        }

        try {
            val parsedDate = LocalDate.parse(date, BankStatementCSVParser.dateTimeFormatter)
            if (parsedDate.isAfter(LocalDate.now())) {
                notification.addError("Date can not be in the future")
            }
        } catch (e: DateTimeParseException) {
            notification.addError("Invalid format for date ${date}")
        }

        if (amount.toDoubleOrNull() == null) {
            notification.addError("Invalid format for amount ${amount}")
        }

        return notification
    }
}