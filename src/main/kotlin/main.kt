import redis.JedisManager
import redis.clients.jedis.Jedis
import kotlin.system.measureTimeMillis

fun main() {

    testJRedis()
}

fun testJRedis() {
    val jedis = Jedis("127.0.0.1", 6379)

    val dataManager = JedisManager(jedis)
    dataManager.deleteAllPersonData()

    val create1000Time = measureTimeMillis {
        dataManager.createPersonData(1000)
    }
    println("create1000Time:${create1000Time.toDouble() / 1000}")

    val create10000Time = measureTimeMillis {
        dataManager.createPersonData(10000)
    }
    println("create10000Time:${create10000Time.toDouble() / 1000}")

    val queryAllTime = measureTimeMillis {
        println("size:${dataManager.queryAllPersonData().size}")
    }
    println("queryAllTime:${queryAllTime.toDouble() / 1000}")

    val deleteAllTime = measureTimeMillis {
        dataManager.deleteAllPersonData()
    }
    println("deleteAllTime:${deleteAllTime.toDouble() / 1000}")

    jedis.close()
}