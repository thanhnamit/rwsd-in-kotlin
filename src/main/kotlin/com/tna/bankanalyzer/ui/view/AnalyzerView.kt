package com.tna.bankanalyzer.ui.view

import tornadofx.View
import tornadofx.borderpane

class AnalyzerView: View() {
    override val root = borderpane {
        top<MenuView>()
        center<TabpaneView>()
    }
}