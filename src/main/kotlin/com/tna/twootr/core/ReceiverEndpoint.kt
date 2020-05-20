package com.tna.twootr.core

/**
 * the interface that receives events from the core, to be implemented by adapters
 */
interface ReceiverEndpoint {
    fun onTwoot(twoot: Twoot)
}