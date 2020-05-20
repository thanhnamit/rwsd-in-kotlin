package com.tna.twootr

import com.tna.twootr.core.ReceiverEndpoint
import com.tna.twootr.core.Twoot
import io.kotest.matchers.collections.shouldContain


class MockReceiverEndpoint: ReceiverEndpoint {
    private val receivedTwoots: MutableList<Twoot> = mutableListOf()

    override fun onTwoot(twoot: Twoot) {
        receivedTwoots.add(twoot)
    }

    fun verifyOnTwoot(twoot: Twoot) {
        receivedTwoots.shouldContain(twoot)
    }
}