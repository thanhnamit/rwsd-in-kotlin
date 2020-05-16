package com.tna.ruleengine

import com.tna.ruleengine.domain.Action
import com.tna.ruleengine.domain.Condition
import com.tna.ruleengine.domain.Rule

class RuleBuilder {
    companion object {
        private lateinit var condition: Condition
        private lateinit var action: Action

        fun `when`(condition: Condition) = apply { this.condition = condition }

        fun then(action: Action) = apply { this.action = action }

        fun build(): Rule {
            return DefaultRule(this.condition, this.action)
        }
    }
}