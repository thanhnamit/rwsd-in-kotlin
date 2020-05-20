package com.tna.twootr.core

/**
 * class that sends events to the core, to be invoked by adapters
 * user: User the owner of this endpoint (valid authentication)
 */
class SenderEndpoint(val user: User, val twootr: Twootr) {

    fun onSendTwoot(id: String, content: String): Position {
        return twootr.onSendTwoot(id, user, content)
    }

    fun onFollow(otherUserId: String): FollowStatus {
        // pass message to core object twootr
        return twootr.onFollow(user, otherUserId)
    }

    fun onLogoff() {
        user.onLogoff()
    }

    fun onDeleteTwoot(id: String): DeleteStatus {
        return twootr.onDeleteTwoot(user.userId, id)
    }
}