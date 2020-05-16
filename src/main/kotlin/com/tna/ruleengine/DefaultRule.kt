package com.tna.ruleengine

import com.tna.ruleengine.domain.Action
import com.tna.ruleengine.domain.Condition
import com.tna.ruleengine.domain.Facts
import com.tna.ruleengine.domain.Rule

class DefaultRule(val condition: Condition, val action: Action): Rule {
    override fun perform(facts: Facts) {
        if (condition.evaluate(facts)) {
            action.execute(facts)
        }
    }
}