package com.tna.bankanalyzer.exporter

import com.tna.bankanalyzer.domain.Exporter
import com.tna.bankanalyzer.domain.SummaryStatistics

class HtmlExporter: Exporter {
    override fun export(summaryStatistics: SummaryStatistics): String {
        return """
            <!DOCTYPE html>
            <html lang="en">
            <head>
              <meta charset="UTF-8">
              <meta name="viewport" content="width=device-width, initial-scale=1.0">
              <title>Bank Transaction Report</title>
            </head>
            <body>
              <ul>
                <li><strong>The sum is ${summaryStatistics.sum} </strong></li>
                <li><strong>The max is ${summaryStatistics.max} </strong></li>
                <li><strong>The min is ${summaryStatistics.min} </strong></li>
                <li><strong>The average is ${summaryStatistics.average} </strong></li>
              </ul>
            </body>
            </html>
        """.trimIndent()
    }
}