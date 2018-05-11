package model

import model.elements.Location.Companion.HOME
import model.elements.Location.Companion.STREET
import model.elements.Location.Companion.location

object GameSession {
    private lateinit var character: Character
    private val locations = arrayListOf(
            location(HOME),
            location(STREET)
    )

    fun newGame() {
        character = Character()
    }

    fun handleCommand(command: ArrayList<String>) {

    }
}