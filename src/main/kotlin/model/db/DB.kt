package model.db

import model.constants.Variables.ITEMS
import model.constants.Variables.LOCATIONS
import model.constants.Variables.SECTIONS
import model.constants.Variables.SEPARATOR
import model.constants.Variables.SURVIVOR
import model.db.entity.*
import org.hibernate.SessionFactory
import org.hibernate.Transaction
import org.hibernate.cfg.Configuration
import java.io.File

object DB {
    private var sessionFactory: SessionFactory? = null
    private var transaction: Transaction? = null

    private fun sessionFactory() = if (sessionFactory == null || sessionFactory!!.isClosed) {
        sessionFactory = Configuration().configure().buildSessionFactory()
        sessionFactory!!
    } else sessionFactory!!


    fun loadLocationFromDB(id: String): Location {
        sessionFactory().use {
            it.openSession().use {
                transaction = it.beginTransaction()
                val location = it.load(Location::class.java, id)!!
                transaction!!.commit()
                return location
            }
        }
    }

    private fun addToDB(any: Any) {
        sessionFactory().openSession().use {
            transaction = it.beginTransaction()
            it.save(any)
            transaction!!.commit()
        }
    }

    private fun createAndFillDB() {
        val locations = arrayListOf<Location>()
        val sections = arrayListOf<Section>()

        var location = Location()
        val section = Section()
        val item = Item()

        File(LOCATIONS).forEachLine {
            when {
                it.startsWith("key") -> location.key = it.s(1)
                it.startsWith("name") -> {
                    location.name = it.s(1)
                    addToDB(location)
                    locations.add(location)
                }
            }
        }

        File(SECTIONS).forEachLine {
            when {
                it.startsWith("location") -> {
                    section.location = it.s(1)
                    section.locationId = locations.find { location -> location.key == it.s(1) }
                }
                it.startsWith("key") -> section.key = it.s(1)
                it.startsWith("name") -> {
                    section.name = it.s(1)
                    sections.add(section)
                    addToDB(section)
                }
                it.startsWith("description") ->
                    addToDB(Description(it.s(1), it.s(2), section))
                it.startsWith("direction") ->
                    addToDB(Direction(it.s(1), it.s(2), it.s(3)))
            }
        }

        File(ITEMS).forEachLine {
            when {
                it.startsWith("placement") -> {
                    item.placement = it.s(1)
                    item.locationId = locations.find { location -> location.key == it.s(1).split(", ")[0] }
                    item.sectionId = sections.find { section -> section.key == it.s(1).split(", ")[1] }
                }
                it.startsWith("key") -> item.key = it.s(1)
                it.startsWith("weight") -> item.weight = it.s(1).toDouble()
                it.startsWith("containable") -> item.containable = true
                it.startsWith("takeable") -> item.takeable = true
                it.startsWith("name") -> {
                    item.name = it.s(1)
                    addToDB(item)
                }
                it.startsWith("call") -> it.s(1).split(", ").forEach { addToDB(Call(it, item)) }
                it.startsWith("description") ->
                    addToDB(Description(it.s(1), it.s(2), item))
            }
        }
    }

    private fun String.s(index: Int) = split(SEPARATOR)[index]

    @JvmStatic
    fun main(args: Array<String>) {
        File(SURVIVOR).delete()

        sessionFactory().use {
            createAndFillDB()
        }
    }
}