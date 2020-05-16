package com.tna.dms

import com.tna.dms.domain.Importer
import com.tna.dms.domain.Attributes
import com.tna.dms.domain.Document
import com.tna.dms.domain.DocumentTypes
import java.io.File
import javax.imageio.ImageIO

class ImageImporter: Importer {
    override fun importFile(file: File): Document {
        val attributes = mutableMapOf<String, String>()
        attributes[Attributes.PATH] = file.path
        val image = ImageIO.read(file)

        attributes[Attributes.WIDTH] = image.width.toString()
        attributes[Attributes.HEIGHT] = image.height.toString()
        attributes[Attributes.TYPE] = DocumentTypes.IMAGE_TYPE

        return Document(attributes)
    }
}