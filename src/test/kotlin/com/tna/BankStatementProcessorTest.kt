package com.tna

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.Month

class BankStatementProcessorTest {
    private val parser: BankStatementCSVParser = BankStatementCSVParser()

    @Test
    fun `should find max in date range`() {
        val lines = """
            10-01-2017,-50,Tesco
            11-02-2017,100.0,Tesco
            12-02-2017,120.0,Tesco
            13-02-2017,90,Tesco
            14-02-2017,80,Tesco
            15-02-2017,200,Tesco
        """.trimIndent()
        val trans = parser.parseLinesFrom(lines.split("\n").toList())
        val processor = BankStatementProcessor(trans)
        val max = processor.findMaxInDateRange(LocalDate.of(2017, Month.FEBRUARY, 11), LocalDate.of(2017, Month.FEBRUARY, 14))
        max.shouldBe(120.0)
    }

    @Test
    fun `should return histogram by months and desc`() {
        val lines = """
            10-01-2017,-50.0,Tesco
            11-02-2017,100.0,Tesca
            12-03-2017,120.0,Tesco
            13-02-2017,90.0,Tesco
            14-03-2017,80.0,Tesca
            14-03-2017,80.0,Tesca
            14-03-2017,80.0,Tesca
            15-04-2017,200.0,Tesca
            15-05-2017,200.0,Tesco
            15-01-2017,200.0,Tesco
        """.trimIndent()

        val groupedLines = """
            10-01-2017,-50.0,Tesco
            15-01-2017,200.0,Tesco
            11-02-2017,100.0,Tesca  
            13-02-2017,90.0,Tesco
            12-03-2017,120.0,Tesco
            14-03-2017,80.0,Tesca
            14-03-2017,80.0,Tesca
            14-03-2017,80.0,Tesca
            15-04-2017,200.0,Tesca
            15-05-2017,200.0,Tesco 
        """.trimIndent()

        val trans = parser.parseLinesFrom(lines.split("\n").toList())
        val processor = BankStatementProcessor(trans)
        val resultTrans = processor.getHistogramGroupByMonthAndDesc()

        val expectedTrans = parser.parseLinesFrom(groupedLines.split("\n").toList())
        resultTrans.shouldBe(expectedTrans)
    }
}