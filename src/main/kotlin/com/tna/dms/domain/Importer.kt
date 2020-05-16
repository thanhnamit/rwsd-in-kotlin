package com.tna.dms.domain

import com.tna.dms.domain.Document
import java.io.File

interface Importer {
    fun importFile(file: File): Document
}