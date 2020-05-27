package com.tna.dms

import com.tna.dms.domain.*
import java.io.File

class InvoiceImporter: Importer {
    companion object {
        const val NAME_PREFIX = "Dear "
        const val AMOUNT_PREFIX = "Amount: "
    }

    override fun importFile(file: File): Document {
        val textFile = TextFile(file)

        textFile.addLineSuffix(NAME_PREFIX, Attributes.PATIENT)
        textFile.addLineSuffix(AMOUNT_PREFIX, Attributes.AMOUNT)

        val attributes = textFile.attributes
        attributes[Attributes.TYPE] = DocumentTypes.INVOICE_TYPE

        return Document(attributes)
    }
}