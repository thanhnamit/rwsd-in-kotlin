package com.tna.twootr

import com.tna.twootr.core.*
import com.tna.twootr.core.repository.InMemoryTwootRepository
import com.tna.twootr.core.repository.InMemoryUserRepository
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.mockk.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class TwootrTest {
    private val receiverEndpoint: ReceiverEndpoint = mockk<ReceiverEndpoint>()
    private val twootRepository = spyk<InMemoryTwootRepository>()
    private val userRepository = InMemoryUserRepository()

    private var twootr: Twootr? = null
    private var senderEndpoint: SenderEndpoint? = null

    companion object {
        val POSITION_1 = Position(0)
    }

    @BeforeEach
    fun setUp() {
        every { receiverEndpoint.onTwoot(any()) } returns Unit
        twootr = Twootr(userRepository, twootRepository)
        twootr!!.onRegisterUser(USER_ID, PASSWORD).shouldBe(RegistrationStatus.SUCCESS)
        twootr!!.onRegisterUser(OTHER_USER_ID, PASSWORD).shouldBe(RegistrationStatus.SUCCESS)
    }

    @Test
    fun `should not register duplicate users`() {
        twootr!!.onRegisterUser(USER_ID, PASSWORD).shouldBe(RegistrationStatus.DUPLICATE)
    }

    @Test
    fun `should be able to authenticate user`() {
        logon()
    }

    @Test
    fun `should not authenticate user with wrong password`() {
        val endpoint = twootr!!.onLogon(USER_ID, "bad pass", receiverEndpoint)
        endpoint.shouldBeNull()
    }

    @Test
    fun `should not authenticate unknown user`() {
        val endpoint = twootr!!.onLogon(NOT_A_USER, "bad pass", receiverEndpoint)
        endpoint.shouldBeNull()
    }

    @Test
    fun `should follow valid user`() {
        logon()

        senderEndpoint!!.onFollow(OTHER_USER_ID).shouldBe(FollowStatus.SUCCESS)
    }

    @Test
    fun `should not duplicate follow valid user`() {
        logon()

        senderEndpoint!!.onFollow(OTHER_USER_ID).shouldBe(FollowStatus.SUCCESS)

        senderEndpoint!!.onFollow(OTHER_USER_ID).shouldBe(FollowStatus.ALREADY_FOLLOWING)
    }

    @Test
    fun `should not follow invalid user`() {
        logon()

        senderEndpoint!!.onFollow(NOT_A_USER).shouldBe(FollowStatus.INVALID_USER)
    }

    @Test
    fun `should receive twoots from followed user`() {
        val id = "1"

        logon()

        senderEndpoint!!.onFollow(OTHER_USER_ID).shouldBe(FollowStatus.SUCCESS)

        val otherEndpoint = otherLogon()
        otherEndpoint.onSendTwoot(id, TWOOT)

        verify { twootRepository.add(id, OTHER_USER_ID, TWOOT) }
        verify { receiverEndpoint.onTwoot(Twoot(id, OTHER_USER_ID, TWOOT, POSITION_1)) }
    }

    @Test
    fun `should not receive twoot after logged off`() {
        val id = "1"

        userFollowsOtherUserAndLogOff()

        val otherEndpoint = otherLogon()
        otherEndpoint.onSendTwoot(id, TWOOT)

        verify { receiverEndpoint wasNot called }
    }

    @Test
    fun `should receive replay of twoots after log off`() {
        val id = "1"

        userFollowsOtherUserAndLogOff()

        val otherEndpoint = otherLogon()
        otherEndpoint.onSendTwoot(id, TWOOT)

        logon()

        verify { receiverEndpoint.onTwoot(twootAt(id, POSITION_1)) }
    }

    @Test
    fun `should delete twoots`() {
        val id = "1"

        userFollowsOtherUserAndLogOff()

        val otherEndpoint = otherLogon()
        otherEndpoint.onSendTwoot(id, TWOOT)

        val deleteStatus = otherEndpoint.onDeleteTwoot(id)

        logon()

        deleteStatus.shouldBe(DeleteStatus.SUCCESS)
        verify { receiverEndpoint wasNot called }
    }

    @Test
    fun `should not delete future position twoot`() {
        logon()

        senderEndpoint!!.onDeleteTwoot("DAS").shouldBe(DeleteStatus.UNKNOWN_TWOOT)
    }

    @Test
    fun `should not delete other users twoots`() {
        val id = "1"

        userFollowsOtherUserAndLogOff()

        val otherEndpoint = otherLogon()
        otherEndpoint.onSendTwoot(id, TWOOT)

        val deleteStatus = senderEndpoint!!.onDeleteTwoot(id)

        twootRepository.get(id).shouldNotBeNull()
        deleteStatus.shouldBe(DeleteStatus.NOT_YOUR_TWOOT)
    }

    fun userFollowsOtherUserAndLogOff() {
        logon()

        senderEndpoint!!.onFollow(OTHER_USER_ID)

        senderEndpoint!!.onLogoff()
    }


    fun otherLogon(): SenderEndpoint {
        val mock = mockk<ReceiverEndpoint>()
        every { mock.onTwoot(any()) } returns Unit
        return logon(OTHER_USER_ID, mock)
    }

    fun logon() {
        this.senderEndpoint = logon(USER_ID, receiverEndpoint)
    }

    fun logon(userId: String, receiverEndpoint: ReceiverEndpoint): SenderEndpoint {
        val endpoint = twootr!!.onLogon(userId, PASSWORD, receiverEndpoint)
        endpoint.shouldNotBeNull()
        return endpoint
    }
}