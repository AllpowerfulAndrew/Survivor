package model.elements

import model.db.DB
import model.elements.Section.Companion.fillSections

interface Location {
    val sections: ArrayList<Section>
    val key: String
    val name: String


    companion object {
        const val HOME = "Home"
        const val STREET = "Street"

        fun location(name: String): Location {
            val dbLocation = DB.loadLocationFromDB(name)

            return object : Location {
                override val key = dbLocation.key!!
                override val name = dbLocation.name!!
                override val sections = fillSections(dbLocation)
            }
        }
    }
}