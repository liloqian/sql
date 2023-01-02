package com.leo.pic

import com.leo.pic.PicGenerateUtil.PIC_KEY_LIST
import com.leo.pic.PicGenerateUtil.ROOT_PATH
import com.leo.pic.PicGenerateUtil.generatePicAndSave
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.io.File


object PicGenerateUtil {

    private const val PIC_URL_ZSY = "https://api.r10086.com/img-api.php?zsy="

    private const val PIC_URL_TYPE = "https://api.r10086.com/img-api.php?type="

    private const val PIC_URL_PPT = " https://api.r10086.com/PPT/PPT.php?PPT="

    const val ROOT_PATH = "./ktor_happy_day/src/main/resources/pic/"

    val PIC_KEY_LIST = mapOf<String, String>(
        "原神" to PIC_URL_ZSY,
        "我的世界系列1" to PIC_URL_TYPE,
        "王者荣耀" to PIC_URL_TYPE,
        "原神" to PIC_URL_PPT,
        "神奇宝贝" to PIC_URL_ZSY
    )

    /**
     * openApi https://img.r10086.com/
     */
    suspend fun generatePicAndSave(rootPath: String, client: HttpClient, num: Int, urlPath: Pair<String, String>) {
        repeat(num) {
            println("request key:${urlPath.second} $it")
            if (urlPath.second !in PIC_KEY_LIST) {
                println("key:${urlPath.second} is not valid.")
                return@repeat
            }
            val response = client.get("${urlPath.first}${urlPath.second}")
            val fileName = response.request.url.fullPath.split("/").lastOrNull()
            withContext(Dispatchers.IO) {
                File("$rootPath${urlPath.second}").apply {
                    if (!exists()) {
                        mkdir()
                    }
                }
                File("$rootPath${urlPath.second}/$fileName").apply {
                    if (exists() && length() > 0) {
                        println("$fileName file already exist...")
                        return@withContext
                    }
                    println("fileName:$fileName absName:${this.absolutePath}")
                    writeBytes(response.body())
                }
            }
        }
    }

    fun getPicFilePath(key: String = "原神"): List<String> {
        val path = mutableListOf<String>()
        File(ROOT_PATH).listFiles()?.filter { it.path.contains(key) }?.forEach {
            it.listFiles()?.filter { it.length() > 0 }?.forEach { file ->
                path.add("$key/${file.name}")
            }
        }
        return path
    }
}

fun main() = runBlocking {
    val client = HttpClient(CIO)
    val jobs = mutableListOf<Job>()
    PIC_KEY_LIST.forEach {
        jobs.add(async {
            generatePicAndSave(ROOT_PATH, client, 100, Pair(it.value, it.key))
        })
    }
    jobs.forEach {
        it.join()
    }
    client.close()
}