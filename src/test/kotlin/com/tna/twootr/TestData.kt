package com.tna.twootr

import com.tna.twootr.core.KeyGenerator
import com.tna.twootr.core.Position
import com.tna.twootr.core.Twoot

const val USER_ID = "Joe"
const val OTHER_USER_ID = "John"
const val NOT_A_USER = "Jack"
val SALT: ByteArray = KeyGenerator.newSalt()
const val PASSWORD = "password"
val PASSWORD_BYTES: ByteArray = KeyGenerator.hash(PASSWORD, SALT)
const val TWOOT = "Hello World!"
const val TWOOT_2 = "Bye World!"

fun twootAt(id: String, position: Position): Twoot {
    return Twoot(id, OTHER_USER_ID, TWOOT, position)
}