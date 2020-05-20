package com.tna.twootr.core

class User(val userId: String, val password: ByteArray, val salt: ByteArray, var lastSeenPosition: Position) {
    private val followers = mutableSetOf<User>()
    private val following = mutableSetOf<String>()
    private var receiverEndpoint: ReceiverEndpoint? = null

    fun followers(): Set<User> {
        return followers.toSet()
    }

    fun getFollowing(): Set<String> {
        return following.toSet()
    }

    fun addFollower(user: User): FollowStatus {
        return if (followers.add(user)) {
            user.following.add(userId)
            FollowStatus.SUCCESS
        } else FollowStatus.ALREADY_FOLLOWING
    }

    fun isLoggedOn(): Boolean {
        return receiverEndpoint != null
    }

    fun receiveTwoot(twoot: Twoot): Boolean {
        return if (isLoggedOn()) {
            receiverEndpoint!!.onTwoot(twoot)
            lastSeenPosition = twoot.position
            true
        } else false
    }

    fun onLogon(receiverEndpoint: ReceiverEndpoint) {
        this.receiverEndpoint = receiverEndpoint
    }

    fun onLogoff() {
        receiverEndpoint = null
    }
}