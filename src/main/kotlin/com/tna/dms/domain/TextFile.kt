package com.tna.dms.domain

import java.io.File
import java.nio.file.Files
import kotlin.streams.toList

/**
 * Domain class with shared functions for text documents
 */
class TextFile(val file: File) {
    val lines: List<String>

    var attributes: MutableMap<String, String>
        get

    init {
        attributes = mutableMapOf()
        attributes[Attributes.PATH] = file.path
        lines = Files.lines(file.toPath()).toList()
    }

    fun addLines(start: Int, isEnd: (String) -> Boolean, attributeName: String): Int {
        var accumulator: String = ""
        var lineNo = start
        for (lineNumber in start until lines.size) {
            val line = lines[lineNumber]
            if (isEnd(line)) break
            accumulator += line
            accumulator += "\n"
            lineNo += 1
        }
        attributes[attributeName] = accumulator.trim()
        return lineNo
    }

    fun addLineSuffix(prefix: String, attributeName: String) {
        for (line in lines) {
            if (line.startsWith(prefix)) {
                attributes[attributeName] = line.substring(prefix.length)
                break
            }
        }
    }

}
