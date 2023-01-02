package com.leo.plugins

import com.leo.person.Person
import com.leo.person.PersonUtil
import com.leo.pic.PicGenerateUtil
import io.ktor.server.routing.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.response.*
import io.ktor.server.request.*
import java.io.File
import java.util.Random

fun Application.configureRouting() {

    routing {
        get("/") {
            call.respondText("Hello World!")
        }

        static("/pic") {
            resources("pic")
        }

        get("/person") {
           call.respond(generateRandomPerson())
        }

        get("/pic") {
            call.respondFile(File(generateRandomPic()))
        }
    }
}

fun generateRandomPic(): String {
    val random = Random()
    val filePaths = PicGenerateUtil.getPicFilePath()
    repeat(5) {
        filePaths.getOrNull(random.nextInt() % (filePaths.size - 1))?.also {
            return "ktor_happy_day/src/main/resources/pic/$it"
        }
    }
    return "ktor_happy_day/src/main/resources/pic/原神/wallhaven-wqw5rx.jpg"
}

fun generateRandomPerson(): List<Person> {
    val ret = mutableListOf<Person>()
    PicGenerateUtil.getPicFilePath().forEach {
        ret.add(PersonUtil.randomPerson().apply {
            pic = "http://0.0.0.0:8080/pic/$it"
        })
    }
    return ret
}