package com.tna.dms

import com.sun.tools.doclets.internal.toolkit.util.DocPaths.RESOURCES
import com.tna.dms.domain.Attributes
import com.tna.dms.domain.Document
import com.tna.dms.exception.UnknownFileTypeException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.string.shouldBeEqualIgnoringCase
import org.junit.jupiter.api.Test
import java.io.File
import java.io.FileNotFoundException
import java.time.Clock.system


class DocumentManagementSystemTest {
    companion object {
        val RESOURCE = "src" + File.separator + "test" + File.separator + "resources" + File.separator
        val LETTER = RESOURCE + "patient.letter"
        val REPORT = RESOURCE + "patient.report"
        val XRAY = RESOURCE + "xray.jpg"
        val INVOICE = RESOURCE + "patient.invoice"
        val JOE_BLOGGS = "Joe Bloggs"
    }

    private val dms = DocumentManagementSystem()

    @Test
    fun `should import file`() {
        dms.importFile(LETTER)
        val document = onlyDocument()
        assertAttributeEquals(document, Attributes.PATH, LETTER)
    }

    @Test
    fun `should import letter attributes`() {
        dms.importFile(LETTER)
        val document = onlyDocument()
        assertAttributeEquals(document, Attributes.PATIENT, JOE_BLOGGS)
        assertAttributeEquals(document, Attributes.ADDRESS,
            "123 Fake Street\n" +
                    "Westminster\n" +
                    "London\n" +
                    "United Kingdom")
        assertAttributeEquals(document, Attributes.BODY,
            "We are writing to you to confirm the re-scheduling of your appointment\n" +
                    "with Dr. Avaj from 29th December 2016 to 5th January 2017.")
        assertAttributeEquals(document, Attributes.TYPE, "LETTER")
    }

    @Test
    fun `should import report attributes`() {
        dms.importFile(REPORT)
        assertIsReport(onlyDocument())
    }

    @Test
    fun `should import image attributes`() {
        dms.importFile(XRAY)
        val document = onlyDocument()
        assertAttributeEquals(document, Attributes.WIDTH, "320")
        assertAttributeEquals(document, Attributes.HEIGHT, "179")
        assertTypeIs("jpg", document)
    }

    @Test
    fun `should import invoice attributes`() {
        dms.importFile(INVOICE)
        val document = onlyDocument()
        assertAttributeEquals(document, Attributes.PATIENT, JOE_BLOGGS)
        assertAttributeEquals(document, Attributes.AMOUNT, "$100")
        assertTypeIs("INVOICE", document)
    }

    @Test
    fun `should be able to search file by attributes`() {
        dms.importFile(LETTER)
        dms.importFile(REPORT)
        dms.importFile(XRAY)
        val documents: List<Document> = dms.search("patient:Joe,body:Diet Coke")
        documents.shouldHaveSize(2)
        assertIsReport(documents[1])
    }

    @Test
    fun `should not import missing file`() {
        shouldThrow<FileNotFoundException> {
            dms.importFile("gobbledygook.letter")
        }
    }

    @Test
    fun `should not import unknown file`() {
        shouldThrow<UnknownFileTypeException> {
            dms.importFile(RESOURCES.toString() + "unknown.txt")
        }
    }

    private fun assertIsReport(document: Document) {
        assertAttributeEquals(document, Attributes.PATIENT, JOE_BLOGGS)
        assertAttributeEquals(
            document, Attributes.BODY,
            """
                On 5th January 2017 I examined Joe's teeth.
                We discussed his switch from drinking Coke to Diet Coke.
                No new problems were noted with his teeth.
                """.trimIndent()
        )
        assertTypeIs("REPORT", document)
    }

    private fun assertTypeIs(type: String, document: Document) {
        assertAttributeEquals(document, Attributes.TYPE, type)
    }

    private fun assertAttributeEquals(document: Document, attributeName: String, expectedValue: String) {
        document.getAttribute(attributeName).shouldBeEqualIgnoringCase(expectedValue)
    }

    private fun onlyDocument(): Document {
        val documents = dms.contents()
        documents.shouldHaveSize(1)
        return documents[0]
    }
}