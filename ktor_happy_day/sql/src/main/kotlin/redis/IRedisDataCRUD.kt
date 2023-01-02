package redis

import com.leo.core.IDataDB
import redis.clients.jedis.Jedis

interface IRedisDataCRUD<T: IDataDB> {

    val jedis: Jedis

    fun saveData(data: T)

    fun updateData(data: T)

    fun deleteData(key: String)

    fun queryData(key: String): String
}

class RedisDataCRUD<T: IDataDB>(override val jedis: Jedis) : IRedisDataCRUD<T> {

    override fun saveData(data: T) {
        jedis.set(data.key, data.data)
    }

    override fun updateData(data: T) {
        jedis.set(data.key, data.data)
    }

    override fun deleteData(key: String) {
        jedis.del(key)
    }

    override fun queryData(key: String): String {
        return jedis.get(key) ?: ""
    }

}