package com.tna.twootr

import com.tna.twootr.core.Position
import com.tna.twootr.core.Twoot
import com.tna.twootr.core.TwootQuery
import com.tna.twootr.core.TwootRepository
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.mockk.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

abstract class AbstractTwootRepositoryTest {
    private val twootQuery: TwootQuery = TwootQuery()
    private val callback: (Twoot) -> Boolean = mockk()
    protected abstract var repository: TwootRepository

    @BeforeEach
    fun setUp() {
        every { callback.invoke(any()) } returns true
    }

    @AfterEach
    fun clear() {
        repository.clear()
    }

    @Test
    fun `should load twoots from position`() {
        val pos1 = add("1", TWOOT)
        val pos2 = add("2", TWOOT_2)

        repository.query(twootQuery.inUsers(USER_ID).lastSeenPosition(pos1), callback)
        verify { callback.invoke(Twoot("2", USER_ID, TWOOT_2, pos2)) }
    }

    @Test
    fun `should get twoots from position`() {
        val id = "1"
        add(id, TWOOT)
        val re = repository.get(id)
        with(re!!) {
            shouldNotBeNull()
            id.shouldBe(id)
            senderId.shouldBe(USER_ID)
            content.shouldBe(TWOOT)
        }
    }

    @Test
    fun `should delete twoots from position`() {
        val id = "1"

        val twoot = repository.add(id, USER_ID, TWOOT)
        repository.delete(twoot)
        repository.get(id).shouldBeNull()
    }

    @Test
    fun `should only load twoots from followed user`() {
        add("1", TWOOT)
        repository.query(twootQuery.lastSeenPosition(Position.INITIAL_POSITION), callback)
    }

    fun add(id: String, content: String): Position {
        val twoot = repository.add(id, USER_ID, content)
        twoot.senderId.shouldBe(USER_ID)
        twoot.content.shouldBe(content)
        return twoot.position
    }
}