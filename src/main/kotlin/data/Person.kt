package data

import com.apifan.common.random.source.AreaSource
import com.apifan.common.random.source.DateTimeSource
import com.apifan.common.random.source.EducationSource
import com.apifan.common.random.source.NumberSource
import com.apifan.common.random.source.PersonInfoSource
import java.util.concurrent.atomic.AtomicInteger

private val idAtomic = AtomicInteger()

data class Person(
    val name: String,
    val nickName: String,
    val qq: String,
    val school: String,
    val birth: String,
    val position: String,
    val age: Int
) : IDataDB {

    override val key: String
        get() = "person${idAtomic.incrementAndGet()}"

    override val data: String
        get() = toString()

}

object PersonUtil {

    /** https://github.com/yindz/common-random */
    fun randomPerson(): Person {
        val name = PersonInfoSource.getInstance().randomMaleChineseName()
        val nickName = PersonInfoSource.getInstance().randomChineseNickName(5)
        val qq = PersonInfoSource.getInstance().randomQQAccount()
        val school = EducationSource.getInstance().randomCollege()
        val birth = DateTimeSource.getInstance().randomDate(2012).toString()
        val position = AreaSource.getInstance().randomAddress()
        val age = NumberSource.getInstance().randomInt(1, 48)
        return Person(name, nickName, qq, school, birth, position, age)
    }

    fun randomPersons(num: Int = 50): List<Person> {
        val list = mutableListOf<Person>()
        repeat(num) {
            list.add(randomPerson())
        }
        return list
    }
}

fun main() {
    println(PersonUtil.randomPerson())
    println("---------------------")
    println(PersonUtil.randomPersons())
}