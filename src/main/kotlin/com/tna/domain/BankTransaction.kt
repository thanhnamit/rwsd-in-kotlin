package com.tna.domain

import java.time.LocalDate

data class BankTransaction(
    val date: LocalDate,
    val amount: Double,
    val description: String
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        val otherObj = (other as? BankTransaction) ?: return false
        return otherObj.amount.compareTo(this.amount) == 0
                && otherObj.date == this.date
                && otherObj.description == this.description
    }
}