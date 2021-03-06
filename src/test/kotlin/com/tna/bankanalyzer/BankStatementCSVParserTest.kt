package com.tna.bankanalyzer

import com.tna.bankanalyzer.domain.BankTransaction
import com.tna.bankanalyzer.exception.CSVLineFormatException
import com.tna.bankanalyzer.exception.CSVSyntaxException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.Month

class BankStatementCSVParserTest {
    private val parser: BankStatementCSVParser =
        BankStatementCSVParser()

    @Test
    fun `should parse one correct line`() {
        val line = "30-01-2017,-50,Tesco"
        val trans = parser.parseFrom(line)
        val expected = BankTransaction(
            LocalDate.of(2017, Month.JANUARY, 30),
            -50.0,
            "Tesco"
        )
        trans.shouldBe(expected)
    }

    @Test
    fun `should parse multiple correct line`() {
        val lines = """
            30-01-2017,-50,Tesco
            30-01-2018,100,Tesco
        """.trimIndent()
        val trans = parser.parseLinesFrom(lines.split("\n").toList())
        trans.shouldHaveSize(2)
    }

    @Test
    fun `should throw CSV syntax exception`() {
        shouldThrow<CSVSyntaxException> {
            parser.parseFrom("300,44")
        }
    }

    @Test
    fun `should throw CSV line format exception`() {
        shouldThrow<CSVLineFormatException> {
            parser.parseFrom("300,49,434")
        }
    }
}