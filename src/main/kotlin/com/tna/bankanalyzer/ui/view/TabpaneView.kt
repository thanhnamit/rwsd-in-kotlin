package com.tna.bankanalyzer.ui.view

import com.tna.bankanalyzer.domain.BankTransaction
import com.tna.bankanalyzer.domain.SummaryStatistics
import com.tna.bankanalyzer.ui.controller.UIController
import javafx.scene.chart.CategoryAxis
import javafx.scene.chart.NumberAxis
import tornadofx.*

class TabpaneView: View() {
    val controller: UIController by inject()
    override val root = tabpane {
        tab("Monthly Statistics") {
            linechart("Monthly Statistics", CategoryAxis(), NumberAxis()) {
                multiseries("Income", "Expense") {
                    controller.getMonthlySummary().forEach {
                        data(it.month, it.income, it.expense)
                    }
                }
            }
        }

        tab("All Transactions") {
            tableview(controller.getAllTransactions().asObservable()) {
                readonlyColumn("Date",BankTransaction::date)
                readonlyColumn("Category", BankTransaction::description)
                readonlyColumn("Amount", BankTransaction::amount)
            }
        }

        tab("Summary Statistics") {
            tableview(listOf<SummaryStatistics>(controller.getSummaryStatistics()).asObservable()) {
                readonlyColumn("Sum",SummaryStatistics::sum)
                readonlyColumn("Min", SummaryStatistics::min)
                readonlyColumn("Max", SummaryStatistics::max)
                readonlyColumn("Average", SummaryStatistics::average)
            }
        }
    }
}