package com.tna.twootr

import com.tna.twootr.core.*
import io.kotest.matchers.Matcher
import io.kotest.matchers.MatcherResult
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test


abstract class AbstractUserRepositoryTest {
    private val receiverEndPoint: ReceiverEndpoint = mockk<ReceiverEndpoint>()
    private var repository: UserRepository? = null
    protected abstract fun newRepository(): UserRepository

    @BeforeEach
    open fun setUp() {
        every { receiverEndPoint.onTwoot(any()) } returns Unit
        repository = newRepository()
        repository!!.clear()
    }

    @Test
    open fun `should load saved user`() {
        repository!!.add(userWith(USER_ID))
        repository!!.get(USER_ID).shouldBe(matchesUser())
    }

    @Test
    open fun `should not allow duplicated user`() {
        repository!!.add(userWith(USER_ID)).shouldBeTrue()
        repository!!.add(userWith(USER_ID)).shouldBeFalse()
    }

    @Test
    open fun `should record follower relationship`() {
        val user: User = userWith(USER_ID)
        val otherUser: User = userWith(OTHER_USER_ID)
        repository!!.add(user)
        repository!!.add(otherUser)
        repository!!.follow(user, otherUser)
        val reloadedRepository = newRepository()
        val userReloaded: User? = reloadedRepository.get(USER_ID)
        val otherUserReloaded: User? = reloadedRepository.get(OTHER_USER_ID)
        otherUserReloaded!!.addFollower(userReloaded!!).shouldBe(FollowStatus.ALREADY_FOLLOWING)
    }

    @Test
    open fun `should record position updates`() {
        val id = "1"
        val newPosition = Position(2)
        val user: User = userWith(USER_ID)
        repository!!.add(user)
        user.lastSeenPosition.shouldBe(Position.INITIAL_POSITION)
        user.receiveTwoot(twootAt(id, newPosition))

        repository!!.update(user)
        val reloadedRepository = newRepository()
        val reloadedUser: User? = reloadedRepository.get(USER_ID)
        user.lastSeenPosition.shouldBe(newPosition)
        reloadedUser!!.lastSeenPosition.shouldBe(newPosition)
    }

    @AfterEach
    fun shutdown() {
        repository!!.close()
    }

    private fun userWith(userId: String): User {
        val user = User(userId, PASSWORD_BYTES, SALT, Position.INITIAL_POSITION)
        user.onLogon(receiverEndPoint)
        return user
    }

    private fun matchesUser() = object: Matcher<User> {
        override fun test(user: User): MatcherResult {
            return MatcherResult.invoke(user.userId.equals(USER_ID) && user.password.equals(
                PASSWORD_BYTES), "Id and password do not match", "Match")
        }
    }
}