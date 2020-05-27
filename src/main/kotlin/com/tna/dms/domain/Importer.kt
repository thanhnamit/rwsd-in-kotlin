package com.tna.dms.domain

import java.io.File

interface Importer {
    fun importFile(file: File): Document
}