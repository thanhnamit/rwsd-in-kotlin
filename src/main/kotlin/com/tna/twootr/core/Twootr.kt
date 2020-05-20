package com.tna.twootr.core

import java.util.*

class Twootr(private val userRepository: UserRepository, private val twootRepository: TwootRepository) {
    fun onLogon(userId: String, password: String, receiverEndpoint: ReceiverEndpoint): SenderEndpoint? {
        val authenticatedUser = userRepository
            .get(userId)
            ?.takeIf {
                val hashedPassword = KeyGenerator.hash(password, it.salt)
                Arrays.equals(hashedPassword, it.password)
            }?.also {
                it.onLogon(receiverEndpoint)
                twootRepository.query(
                    TwootQuery().inUsers(it.getFollowing())
                        .lastSeenPosition(it.lastSeenPosition),
                    it::receiveTwoot
                )
                userRepository.update(it)
            }

        return if (authenticatedUser != null) SenderEndpoint(authenticatedUser, this) else null
    }

    fun onRegisterUser(userId: String, password: String): RegistrationStatus {
        val salt = KeyGenerator.newSalt()
        val hashedPassword = KeyGenerator.hash(password, salt)
        val user = User(userId, hashedPassword, salt, Position.INITIAL_POSITION)
        return if (userRepository.add(user)) RegistrationStatus.SUCCESS else RegistrationStatus.DUPLICATE
    }

    fun onFollow(follower: User, otherUserId: String): FollowStatus {
        return userRepository.get(otherUserId)?.let { userRepository.follow(follower, it) } ?: FollowStatus.INVALID_USER
    }

    fun onSendTwoot(id: String, user: User, content: String): Position {
        val userId = user.userId
        val twoot = twootRepository.add(id, userId, content)

        user.followers()
            .filter(User::isLoggedOn)
            .forEach { follower ->
                follower.receiveTwoot(twoot);
                userRepository.update(follower);
            }

        return twoot.position
    }

    fun onDeleteTwoot(userId: String, id: String): DeleteStatus {
        return twootRepository.get(id)
            ?.let { twoot ->
                val canDeleteTwoot = twoot.senderId.equals(userId);
                if (canDeleteTwoot) {
                    twootRepository.delete(twoot);
                }
                return if (canDeleteTwoot) DeleteStatus.SUCCESS else DeleteStatus.NOT_YOUR_TWOOT;
            } ?: DeleteStatus.UNKNOWN_TWOOT
    }
}