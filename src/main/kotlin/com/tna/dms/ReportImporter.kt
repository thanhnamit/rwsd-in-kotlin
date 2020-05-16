package com.tna.dms

import com.tna.dms.domain.Importer
import com.tna.dms.domain.Attributes
import com.tna.dms.domain.Document
import com.tna.dms.domain.DocumentTypes
import com.tna.dms.domain.TextFile
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
