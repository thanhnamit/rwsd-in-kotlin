package com.tna.twootr

import com.tna.twootr.core.UserRepository
import com.tna.twootr.core.repository.InMemoryUserRepository

class InMemoryUserRepositoryTest: AbstractUserRepositoryTest() {
    private val inMemoryUserRepository = InMemoryUserRepository()

    override fun newRepository(): UserRepository {
        return inMemoryUserRepository
    }
}