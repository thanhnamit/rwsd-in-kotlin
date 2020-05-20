package com.tna.twootr.adapter.web

import com.beust.klaxon.JsonObject
import com.beust.klaxon.JsonReader
import com.beust.klaxon.Klaxon
import com.beust.klaxon.Parser
import com.tna.twootr.core.*
import org.java_websocket.WebSocket

class WebSocketEndPoint(val twootr: Twootr, val webSocket: WebSocket): ReceiverEndpoint {
    private val CMD = "cmd"
    private val klaxon: Parser = Parser.default()
    private var senderEndpoint: SenderEndpoint? = null

    fun onMessage(message: String) {
        val json = (klaxon.parse(StringBuilder(message)) as JsonObject?)!!
        val cmd = json.string(CMD)

        when (cmd) {
            "logon" -> {
                val userName = json.string("userName")!!
                val password = json.string("password")!!
                val endpoint = twootr.onLogon(userName, password, this)
                if (endpoint == null) {
                    senderEndpoint = null
                    webSocket.close()
                } else {
                    senderEndpoint = endpoint
                }
            }
            "follow" -> {
                val userName = json.string("userName")!!
                sendStatusUpdate(senderEndpoint!!.onFollow(userName).toString())
            }
            "sendTwoot" -> {
                val id = json.string("id")!!
                val content = json.string("content")!!
                sendPosition(senderEndpoint!!.onSendTwoot(id, content))
            }
            "deleteTwoot" -> {
                val id = json.string("id")!!
                sendStatusUpdate(senderEndpoint!!.onDeleteTwoot(id).toString())
            }
        }
    }

    override fun onTwoot(twoot: Twoot) {
        webSocket.send(String.format(
            "{\"cmd\":\"twoot\", \"user\":\"%s\", \"msg\":\"%s\"}",
            twoot.senderId,
            twoot.content))
    }

    private fun sendPosition(position: Position) {
        webSocket.send(
            String.format(
                "{\"cmd\":\"sent\", \"position\":%s}",
                position.value
            )
        )
    }

    private fun sendStatusUpdate(status: String) {
        webSocket.send(
            String.format(
                "{\"cmd\":\"statusUpdate\", \"status\":\"%s\"}",
                status
            )
        )
    }
}