package com.tna.dms.domain

class Document {
    private val attributes: Map<String, String>

    constructor(attrs: Map<String, String>) {
        this.attributes = attrs
    }

    fun getAttribute(name: String): String? {
        return this.attributes[name]
    }
}
