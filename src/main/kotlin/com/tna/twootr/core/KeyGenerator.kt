package com.tna.twootr.core

import org.bouncycastle.crypto.generators.SCrypt
import java.security.SecureRandom

internal object KeyGenerator {
    private const val SCRYPT_COST = 16384
    private const val SCRYPT_BLOCK_SIZE = 8
    private const val SCRYPT_PARALLELISM = 1
    private const val KEY_LENGTH = 20
    private const val SALT_LENGTH = 16
    private val secureRandom: SecureRandom = SecureRandom()
    fun hash(password: String, salt: ByteArray?): ByteArray {
        val passwordBytes: ByteArray = password.toByteArray(Charsets.UTF_16)
        return SCrypt.generate(
            passwordBytes,
            salt,
            SCRYPT_COST,
            SCRYPT_BLOCK_SIZE,
            SCRYPT_PARALLELISM,
            KEY_LENGTH
        )
    }

    fun newSalt(): ByteArray {
        val salt = ByteArray(SALT_LENGTH)
        secureRandom.nextBytes(salt)
        return salt
    }
}