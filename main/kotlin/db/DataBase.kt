package db

import db.entity.Description
import db.entity.Item
import db.entity.Location
import db.entity.Section
import org.hibernate.SessionFactory
import org.hibernate.Transaction
import org.hibernate.cfg.Configuration
import java.io.File

object DataBase {
    private var sessionFactory: SessionFactory? = null
    private var transaction: Transaction? = null

    fun sessionFactory() = if (sessionFactory == null) {
        sessionFactory = Configuration().configure().buildSessionFactory()
        sessionFactory!!
    } else sessionFactory!!


    fun addToDB(any: Any) {
        sessionFactory().openSession().use {
            transaction = it.beginTransaction()
            it.save(any)
            transaction!!.commit()
        }
    }

    fun getLocation(id: String): Location {
        var location: Location? = null

        sessionFactory!!.openSession().use {
            transaction = it.beginTransaction()
            location = it.load(Location::class.java, id)
            transaction!!.commit()
        }

        return location!!
    }

    fun dropDB() {
        sessionFactory!!.openSession().use {
            transaction = it.beginTransaction()


        }
    }

//    fun createAndFillDB() {
//        val locationsId = hashSetOf<String>()
//        val location = Location()
//        val section = Section()
//        var blockLine = 0
//
//        File("main/resources/sections.txt").forEachLine {
//            if (it.trim().isEmpty()) {
//                location.id = section.id!!.split(" ")[0]
//                locationsId.add(location.id!!)
//                section.location = location
//                addToDB(section)
//                blockLine = 0
//            } else {
//                when (blockLine) {
//                    0 -> section.id = it
//                    else -> addToDB(Description(section.id + " " + it.split("|")[0], it.split("|")[1], section))
//                }
//                blockLine++
//            }
//        }
//        section.location = Location(section.id!!.split(" ")[0])
//        addToDB(section)
//
//        val item = Item()
//        blockLine = 0
//
//        File("main/resources/items.txt").forEachLine {
//            if (it.trim().isEmpty()) {
//                location.id = item.id!!.split(" ")[0]
//                item.location = location
//                addToDB(item)
//                blockLine = 0
//            } else {
//                when (blockLine) {
//                    0 -> item.id = it
//                    1 -> item.name = it
//                    else -> addToDB(Description(item.id + " " + it.split("|")[0], it.split("|")[1], item))
//                }
//                blockLine++
//            }
//        }
//        item.location = Location(item.id!!.split(" ")[0])
//        addToDB(item)
//
//        for (i in locationsId) {
//            location.id = i
//            addToDB(location)
//        }
//    }

    fun createAndFillDB() {
        val locationsId = hashSetOf<String>()
        val location = Location()
        val section = Section()
        var blockLine = 0

        File("main/resources/sections.txt").forEachLine {
            if (it.trim().isEmpty()) {
                location.id = section.id!!.split(" ")[0]
                locationsId.add(location.id!!)
                section.location = location
                addToDB(section)
                blockLine = 0
            } else {
                when (blockLine) {
                    0 -> section.id = it
                    else -> addToDB(Description(section.id + " " + it.split("|")[0], it.split("|")[1], section))
                }
                blockLine++
            }
        }
        section.location = Location(section.id!!.split(" ")[0])
        addToDB(section)

        val item = Item()
        blockLine = 0

        File("main/resources/items.txt").forEachLine {
            if (it.trim().isEmpty()) {
                location.id = item.id!!.split(" ")[0]
                item.location = location
                addToDB(item)
                blockLine = 0
            } else {
                when (blockLine) {
                    0 -> item.id = it
                    1 -> item.name = it
                    else -> addToDB(Description(item.id + " " + it.split("|")[0], it.split("|")[1], item))
                }
                blockLine++
            }
        }
        item.location = Location(item.id!!.split(" ")[0])
        addToDB(item)

        for (i in locationsId) {
            location.id = i
            addToDB(location)
        }
    }

    @JvmStatic
    fun main(args: Array<String>) {

        sessionFactory().use {
            createAndFillDB()
        }
    }
}
