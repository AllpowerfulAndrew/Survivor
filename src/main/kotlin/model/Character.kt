package model

import model.elements.Item

class Character {
    private val inventory = arrayListOf<Item>()

    var location = "Home"
    var section = "Bedroom"
}