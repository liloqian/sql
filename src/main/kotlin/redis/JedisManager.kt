package redis

import data.Person
import data.PersonUtil
import redis.clients.jedis.Jedis

class JedisManager(private val jedis: Jedis) {

    private val crud by lazy {
      RedisDataCRUD<Person>(jedis = jedis)
    }

    fun createPersonData(num: Int) {
        PersonUtil.randomPersons(num).forEach {
            crud.saveData(it)
        }
    }

    fun queryPersonData(key: String): String {
        return crud.queryData(key)
    }

    fun queryAllPersonData(): List<String> {
        val list = mutableSetOf<String>()
        jedis.keys("*").filter { it.startsWith("person") }.forEach {
            list.add(crud.queryData(it))
        }
        list.remove("")
        return list.toList()
    }

    fun deletePersonData(key: String) {
        crud.deleteData(key)
    }

    fun deleteAllPersonData() {
        jedis.keys("*").filter { it.startsWith("person") }.forEach {
            crud.deleteData(it)
        }
    }

    fun updateData(data: Person) {
        crud.updateData(data)
    }
}