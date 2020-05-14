package com.tna.bankanalyzer.ui.view

import tornadofx.View
import tornadofx.item
import tornadofx.menu
import tornadofx.menubar

class MenuView: View() {
    override val root = menubar {
        menu("File") {
            item("New")
            item("Save As")
            item("Quit")
        }
        menu("Edit") {
            item("Copy")
            item("Paste")
        }
    }
}