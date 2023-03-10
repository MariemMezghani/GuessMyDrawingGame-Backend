package mariemmezghani.com

import io.ktor.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.sessions.*
import io.ktor.util.*
import mariemmezghani.com.plugins.*
import mariemmezghani.com.session.DrawingSession

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {

    install(Sessions){
        cookie<DrawingSession>("SESSION")
    }

    intercept(ApplicationCallPipeline.Features){

        if (call.sessions.get("SESSIONS")==null){

            val clientId = call.parameters["client_id"] ?: ""
            call.sessions.set(DrawingSession(clientId = clientId, sessionId = generateNonce()))
        }
    }
    configureSockets()
    configureSerialization()
    configureMonitoring()
    configureRouting()
}
