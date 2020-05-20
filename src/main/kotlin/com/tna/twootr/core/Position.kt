package com.tna.twootr.core

data class Position(val value: Int) {
    companion object {
        val INITIAL_POSITION = Position(-1)
    }

    fun next(): Position {
        return Position(value + 1)
    }
}
