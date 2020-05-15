package com.tna.dms

import com.tna.dms.api.Importer
import com.tna.dms.domain.Document
import com.tna.dms.domain.DocumentTypes
import com.tna.dms.exception.UnknownFileTypeException
import tornadofx.asObservable
import java.io.File
import java.io.FileNotFoundException

class DocumentManagementSystem {
    private val documents = mutableListOf<Document>()
    private val extensionToImporter = mutableMapOf<String, Importer>()

    init {
        extensionToImporter[DocumentTypes.LETTER_TYPE] = LetterImporter()
        extensionToImporter[DocumentTypes.REPORT_TYPE] = ReportImporter()
        extensionToImporter[DocumentTypes.IMAGE_TYPE] = ImageImporter()
        extensionToImporter[DocumentTypes.INVOICE_TYPE] = InvoiceImporter()
    }

    fun importFile(path: String) {
        val file = File(path)
        val ext = file.extension
        val importer = extensionToImporter[ext] ?: throw UnknownFileTypeException("For file: ${path}. Only support letter, report, jpg, invoice")

        if (!file.exists()) throw FileNotFoundException(path)
        val document = importer.importFile(file)
        documents.add(document)
    }

    fun contents(): List<Document> {
        return documents.toList()
    }

    fun search(query: String): List<Document> {
        return documents.filter { Query.parse(query).test(it) }.toList()
    }
}