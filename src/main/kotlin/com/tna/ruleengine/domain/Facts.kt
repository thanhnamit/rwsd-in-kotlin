package com.tna.ruleengine.domain

class Facts {
    var facts: MutableMap<String, String> = mutableMapOf()

    fun getFact(name: String): String? {
        return facts[name]
    }

    fun addFact(name: String, value: String) {
        facts[name] = value
    }
}
