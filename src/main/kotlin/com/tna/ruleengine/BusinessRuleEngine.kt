package com.tna.ruleengine

import com.tna.ruleengine.domain.Action
import com.tna.ruleengine.domain.Facts

class BusinessRuleEngine(val facts: Facts) {
    var actions: MutableList<Action> = mutableListOf()

    fun addAction(action: Action) {
        actions.add(action)
    }

    fun count(): Int {
        return actions.size
    }

    fun run() {
        actions.forEach { it.execute(facts) }
    }
}