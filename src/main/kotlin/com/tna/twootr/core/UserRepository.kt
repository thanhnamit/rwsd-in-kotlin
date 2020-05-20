package com.tna.twootr.core

interface UserRepository : AutoCloseable {
    fun add(user: User): Boolean
    fun get(userId: String): User?
    fun update(user: User)
    fun clear()
    fun follow(follower: User, userToFollow: User): FollowStatus
}