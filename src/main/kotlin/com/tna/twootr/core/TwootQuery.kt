package com.tna.twootr.core

import java.util.*

class TwootQuery {
    var inUsers: Set<String>? = null
        private set
    var lastSeenPosition: Position? = null
        private set

    fun inUsers(vararg inUsers: String?): TwootQuery {
        return inUsers(HashSet<String>(inUsers!!.toList()))
    }

    fun inUsers(inUsers: Set<String>?): TwootQuery {
        this.inUsers = inUsers
        return this
    }

    fun lastSeenPosition(lastSeenPosition: Position?): TwootQuery {
        this.lastSeenPosition = lastSeenPosition
        return this
    }

    fun hasUsers(): Boolean {
        return inUsers != null && !inUsers!!.isEmpty()
    }
}