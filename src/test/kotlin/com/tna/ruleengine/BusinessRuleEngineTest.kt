package com.tna.ruleengine

import com.tna.ruleengine.domain.Action
import com.tna.ruleengine.domain.Condition
import com.tna.ruleengine.domain.Facts
import com.tna.ruleengine.domain.Stage
import io.kotest.matchers.ints.shouldBeExactly
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldBeEqualIgnoringCase
import io.mockk.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test

internal class BusinessRuleEngineTest {
    @Test
    fun `should have no rules initially`() {
        val engine = BusinessRuleEngine(mockk())
        engine.count().shouldBeExactly(0)
    }

    @Test
    fun `should add two actions`() {
        val engine = BusinessRuleEngine(mockk())
        engine.addAction(object: Action {
            override fun execute(mockFacts: Facts) {}
        })
        engine.addAction(object: Action {
            override fun execute(mockFacts: Facts) {}
        })
        engine.count().shouldBeExactly(2)
    }

    @Test
    fun `should execute one action`() {
        val mockFacts: Facts = mockk()
        val engine = BusinessRuleEngine(mockFacts)

        val action: Action = mockk()
        every { action.execute(mockFacts) } returns Unit

        engine.addAction(action)
        engine.run()
        verify { action.execute(mockFacts) }
    }

    @Test
    fun `should performance action with facts`() {
        val mockAction: Action = mockk()
        val mockFacts: Facts = mockk()
        every { mockAction.execute(mockFacts) } returns Unit

        val engine = BusinessRuleEngine(mockFacts)

        engine.addAction(mockAction)
        engine.run()

        verify { mockAction.execute(mockFacts) }
    }

    @Test
    fun `should create rule from builder`() {
        // TODO: ugly kotlin, better to use typealias instead of interface
        val rule = RuleBuilder.`when`(object: Condition {
            override fun evaluate(facts: Facts): Boolean {
                return "CEO" == facts.getFact("jobTitle")
            }
        }).then(object: Action {
            override fun execute(facts: Facts) {
                val name = facts.getFact("name")
                facts.addFact("sentEmail", "true")
            }
        }).build()

        val facts = Facts()
        facts.addFact("jobTitle", "CEO")
        rule.perform(facts)
        facts.getFact("sentEmail").shouldBeEqualIgnoringCase("true")
    }

    @Test
    fun `should run a real action`() {
        val facts = Facts()
        facts.addFact("stage", "LEAD")
        facts.addFact("amount", "10.0")

        val engine = BusinessRuleEngine(facts)
        engine.addAction(object : Action {
            override fun execute(facts: Facts) {
                val dealStage = Stage.valueOf(facts.getFact("stage") ?: throw IllegalArgumentException("No fact found"))
                val amount = (facts.getFact("amount") ?: throw IllegalArgumentException("No amount fact found")).toDouble()
                val forcastedAmount = when (dealStage) {
                    Stage.LEAD -> amount * 0.2
                    Stage.EVALUATING -> amount * 0.5
                    Stage.INTERESTED -> amount * 0.8
                    Stage.CLOSED -> amount
                }
                facts.addFact("forecastedAmount", forcastedAmount.toString())
            }
        })

        engine.run()
        engine.facts.getFact("forecastedAmount").shouldBe("2.0")
    }

    @AfterEach
    fun cleanUp() {
        unmockkAll()
    }
}