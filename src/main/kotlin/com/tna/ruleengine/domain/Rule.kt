package com.tna.ruleengine.domain

@FunctionalInterface
interface Rule {
    fun perform(facts: Facts)
}