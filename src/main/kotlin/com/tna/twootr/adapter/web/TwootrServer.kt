package com.tna.twootr.adapter.web

import com.tna.twootr.core.TwootRepository
import com.tna.twootr.core.Twootr
import com.tna.twootr.core.repository.InMemoryTwootRepository
import com.tna.twootr.core.repository.InMemoryUserRepository
import org.eclipse.jetty.server.Server
import org.eclipse.jetty.servlet.DefaultServlet
import org.eclipse.jetty.servlet.ServletContextHandler
import org.eclipse.jetty.servlet.ServletHolder
import org.java_websocket.WebSocket
import org.java_websocket.handshake.ClientHandshake
import org.java_websocket.server.WebSocketServer
import java.io.IOException
import java.net.InetSocketAddress


class TwootrServer(address: InetSocketAddress?) : WebSocketServer(address, 1) {
    private val twootRepository: TwootRepository = InMemoryTwootRepository()
    private val twootr = Twootr(InMemoryUserRepository(), twootRepository)
    private val socketToEndPoint: MutableMap<WebSocket, WebSocketEndPoint> =
        mutableMapOf<WebSocket, WebSocketEndPoint>()

    override fun onOpen(webSocket: WebSocket, clientHandshake: ClientHandshake) {
        socketToEndPoint[webSocket] = WebSocketEndPoint(twootr, webSocket)
    }

    override fun onClose(webSocket: WebSocket, i: Int, s: String, b: Boolean) {
        socketToEndPoint.remove(webSocket)
    }

    override fun onMessage(webSocket: WebSocket, message: String) {
        val endPoint: WebSocketEndPoint? = socketToEndPoint[webSocket]
        println(message)
        try {
            endPoint!!.onMessage(message)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    override fun onError(webSocket: WebSocket, e: Exception) {
        e.printStackTrace()
    }

    companion object {
        const val STATIC_PORT = 8085
        private const val WEBSOCKET_PORT = 9000
        private const val USER_NAME = "Joe"
        private const val PASSWORD = "ahc5ez2aiV"
        private const val OTHER_USER_NAME = "John"

        @Throws(Exception::class)
        @JvmStatic
        fun main(args: Array<String>) {
            val websocketAddress =
                InetSocketAddress("localhost", WEBSOCKET_PORT)
            val twootrServer = TwootrServer(websocketAddress)
            twootrServer.start()
            System.setProperty("org.eclipse.jetty.LEVEL", "INFO")
            val context = ServletContextHandler(ServletContextHandler.SESSIONS)
            context.resourceBase = System.getProperty("user.dir") + "/src/main/webapp"
            context.contextPath = "/"
            val staticContentServlet = ServletHolder(
                "staticContentServlet", DefaultServlet::class.java
            )
            staticContentServlet.setInitParameter("dirAllowed", "true")
            context.addServlet(staticContentServlet, "/")
            val jettyServer = Server(STATIC_PORT)
            jettyServer.handler = context
            jettyServer.start()
            jettyServer.dumpStdErr()
            jettyServer.join()
        }
    }

    init {
        twootr.onRegisterUser(USER_NAME, PASSWORD)
        twootr.onRegisterUser(OTHER_USER_NAME, PASSWORD)
    }
}