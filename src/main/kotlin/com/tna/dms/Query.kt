package com.tna.dms

import com.tna.dms.domain.Document
import java.util.function.Predicate

class Query: Predicate<Document> {
    private val clauses: Map<String, String>

    companion object {
        fun parse(query: String): Query {
            return Query(query.split(",")
                    .map { it -> it.split(":") }
                    .map { it[0] to it[1] }.toMap())
        }
    }

    private constructor(clauses: Map<String, String>) {
        this.clauses = clauses
    }

    override fun test(doc: Document): Boolean {
        return clauses.all { it ->
            val docValue = doc.getAttribute(it.key)
            val queryValue = it.value
            return docValue != null && docValue.contains(queryValue)
        }
    }
}