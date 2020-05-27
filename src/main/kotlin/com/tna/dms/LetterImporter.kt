package com.tna.dms

import com.tna.dms.domain.*
import java.io.File

class LetterImporter : Importer {

    companion object {
        const val NAME_PREFIX = "Dear "
    }

    override fun importFile(file: File): Document {
        val textFile = TextFile(file)
        textFile.addLineSuffix(NAME_PREFIX, Attributes.PATIENT)

        val lineNumber = textFile.addLines(2, String::isEmpty, Attributes.ADDRESS)
        textFile.addLines(lineNumber + 1, { t -> t.startsWith("regards,")}, Attributes.BODY)

        val attributes = textFile.attributes
        attributes[Attributes.TYPE] = DocumentTypes.LETTER_TYPE

        return Document(attributes)
    }
}
