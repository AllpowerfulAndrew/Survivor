package model.elements

import model.elements.Item.Companion.fillItems

interface Section {
    val key: String
    val name: String
    val items: ArrayList<Item>
    val descriptions: HashMap<String, String>

    companion object {
        const val BATHROOM = "Bathroom"
        const val BEDROOM = "Bedroom"
        const val HALLWAY = "Hallway"
        const val KITCHEN = "Kitchen"
        const val LIVING_ROOM = "Living Room"
        const val ROOF = "Roof"
        const val STAIRCASE_UP = "Staircase Up"
        const val STAIRCASE_DOWN = "Staircase Down"


        fun fillSections(dbLocation: model.db.entity.Location): ArrayList<Section> {
            val sections = arrayListOf<Section>()

            dbLocation.sections!!.forEach {
                sections.add(object : Section {
                    override val key = it.key!!
                    override val name = it.name!!
                    override val items = fillItems(it)
                    override val descriptions = hashMapOf<String, String>()

                    init {
                        it.descriptions!!.forEach {description ->
                            descriptions[description.key!!] = description.value!!
                        }
                    }
                })
            }

            return sections
        }
    }
}