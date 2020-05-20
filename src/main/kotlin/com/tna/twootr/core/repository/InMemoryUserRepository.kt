package com.tna.twootr.core.repository

import com.tna.twootr.core.FollowStatus
import com.tna.twootr.core.User
import com.tna.twootr.core.UserRepository


class InMemoryUserRepository : UserRepository {
    private val userIdToUser: MutableMap<String, User> = mutableMapOf()

    override operator fun get(userId: String): User? {
        return userIdToUser[userId]
    }

    override fun add(user: User): Boolean {
        return userIdToUser.putIfAbsent(user.userId, user) == null
    }

    override fun update(user: User) {}

    override fun follow(follower: User, userToFollow: User): FollowStatus {
        return userToFollow.addFollower(follower)
    }

    override fun clear() {
        userIdToUser.clear()
    }

    override fun close() {}
}