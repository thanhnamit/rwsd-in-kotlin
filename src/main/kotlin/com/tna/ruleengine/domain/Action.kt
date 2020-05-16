package com.tna.ruleengine.domain

@FunctionalInterface
interface Action {
    fun execute(facts: Facts)
}