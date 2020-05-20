package com.tna.twootr.core

data class Twoot(val id: String, val senderId: String, val content: String, val position: Position) {
    fun isAfter(otherPosition: Position): Boolean {
        return position.value > otherPosition.value
    }
}
