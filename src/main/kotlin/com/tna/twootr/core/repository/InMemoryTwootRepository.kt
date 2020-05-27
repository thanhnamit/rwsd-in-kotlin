package com.tna.twootr.core.repository

import com.tna.twootr.core.Position
import com.tna.twootr.core.Twoot
import com.tna.twootr.core.TwootQuery
import com.tna.twootr.core.TwootRepository

class InMemoryTwootRepository: TwootRepository {
    private var twoots = mutableListOf<Twoot>()
    private var currentPosition: Position = Position.INITIAL_POSITION;

    override fun query(twootQuery: TwootQuery, callback: (Twoot) -> Boolean) {
        if (!twootQuery.hasUsers()) {
            return
        }

        val lastSeenPosition = twootQuery.lastSeenPosition
        val inUsers = twootQuery.inUsers

        twoots.filter { inUsers!!.contains(it.senderId) }
            .filter { it.isAfter(lastSeenPosition!!) }
            .forEach { callback.invoke(it) }
    }

    fun queryLoop(twootQuery: TwootQuery, callback: (Twoot) -> Boolean) {
        if (!twootQuery.hasUsers()) {
            return;
        }

        val lastSeenPosition = twootQuery.lastSeenPosition
        val inUsers = twootQuery.inUsers

        for (twoot in twoots) {
            if (inUsers!!.contains(twoot.senderId) && twoot.isAfter(lastSeenPosition!!)) {
                callback.invoke(twoot)
            }
        }
    }

    override fun get(id: String): Twoot? {
        return twoots.firstOrNull { it.id == id }
    }

    override fun delete(twoot: Twoot) {
        twoots.remove(twoot)
    }

    override fun add(id: String, userId: String, content: String): Twoot {
        currentPosition = currentPosition.next()
        val twootPosition = currentPosition.copy()
        val twoot = Twoot(id, userId, content, twootPosition)
        twoots.add(twoot)
        return twoot
    }

    override fun clear() {
        twoots.clear()
    }
}