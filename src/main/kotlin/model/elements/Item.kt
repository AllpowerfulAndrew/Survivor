package model.elements

class Item(val key: String, val name: String, weight: Double = .0, val takeable: Boolean = false, val containable: Boolean = false) {
    var weight = weight
        private set

    private val contents = arrayListOf<Item>()

    val descriptions = hashMapOf<String, String>()
    fun updateWeight(diff: Double) {
        if (weight + diff < 0.01)
            weight = 0.01
        else
            weight += diff
    }

    companion object {
        const val AID_KIT = "Aid Kit"
        const val BED = "Bed"
        const val CLOCK = "Clock"
        const val CUPBOARD = "Cupboard"
        const val DOOR = "Door"
        const val FRIDGE = "Fridge"
        const val STAIRS = "Stairs"
        const val STOVE = "Stove"
        const val WINDOW = "Window"

        fun fillItems(section: model.db.entity.Section): ArrayList<Item> {
            val items = arrayListOf<Item>()

            section.items!!.forEach { bdItem ->
                val item = Item(
                        bdItem.key!!,
                        bdItem.name!!,
                        bdItem.weight,
                        bdItem.takeable,
                        bdItem.containable)

                bdItem.descriptions!!.forEach {
                    item.descriptions[it.key!!] = it.value!!
                }

                items.add(item)
            }

            return items
        }
    }
}