package com.tna.bankanalyzer.domain

class Notification {
    private val errors = mutableListOf<String>()

    fun addError(err: String) {
        errors.add(err)
    }

    fun hasErrors(): Boolean {
        return !errors.isEmpty()
    }

    fun errorMessage(): String {
        return errors.toString()
    }

    fun getErrors(): List<String> {
        return errors.toList()
    }
}