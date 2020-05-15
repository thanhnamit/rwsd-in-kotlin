package com.tna.dms.api

import com.tna.dms.domain.Document
import java.io.File

interface Importer {
    fun importFile(file: File): Document
}