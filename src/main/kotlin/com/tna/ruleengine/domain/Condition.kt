package com.tna.ruleengine.domain

interface Condition {
    fun evaluate(facts: Facts): Boolean
}