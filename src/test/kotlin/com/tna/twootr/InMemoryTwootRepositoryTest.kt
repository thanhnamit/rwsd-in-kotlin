package com.tna.twootr

import com.tna.twootr.core.TwootRepository
import com.tna.twootr.core.repository.InMemoryTwootRepository

class InMemoryTwootRepositoryTest: AbstractTwootRepositoryTest() {
    override var repository: TwootRepository = InMemoryTwootRepository()
}