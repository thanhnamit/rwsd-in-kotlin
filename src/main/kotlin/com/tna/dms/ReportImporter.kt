package com.tna.dms

import com.tna.dms.domain.*
import java.io.File

class ReportImporter : Importer {

    companion object {
        const val NAME_PREFIX = "Patient: "
    }

    override fun importFile(file: File): Document {
        val textFile = TextFile(file)

        textFile.addLineSuffix(NAME_PREFIX, Attributes.PATIENT)
        textFile.addLines(2, { false }, Attributes.BODY)

        val attributes = textFile.attributes
        attributes[Attributes.TYPE] = DocumentTypes.REPORT_TYPE

        return Document(attributes)
    }
}
