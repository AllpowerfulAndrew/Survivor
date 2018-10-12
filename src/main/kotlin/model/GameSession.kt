package model

import javafx.scene.control.Label
import javafx.scene.control.TextArea
import javafx.scene.control.TextField
import model.constants.Action
import model.constants.Action.INVALID
import model.elements.Location
import model.elements.Location.Companion.HOME
import model.elements.Location.Companion.STREET
import model.elements.Location.Companion.location

object GameSession {
    private lateinit var character: Character
    private val locations = arrayListOf<Location>()
    private var lastInput = "\n\n"

    fun newGame() {
        character = Character()
    }

    fun handleAndUpdate(inputField: TextField, lastInputLabel: Label, outputField: TextArea) {
        updateLastInput(inputField, lastInputLabel)
        val combo = extractComboList(inputField.text)
        val command = combo[0]
        val action = actualCommand(command)

        if (action != INVALID)
            make(action)

        inputField.text = ""
    }

    fun selLocations(): Boolean {
        locations.add(location(HOME))
        locations.add(location(STREET))
        return true
    }

    fun valid(text: String): Boolean {
        val combo = extractComboList(text)

        return actualCommand(combo[0]) != INVALID
    }

    private fun actualCommand(command: String): Action {
        for (action in Action.values())
            if (action.hasCommand(command))
                return action

        return INVALID
    }

    fun useLastInput(inputField: TextField) {
        inputField.text = lastInput
        inputField.requestFocus()
        inputField.end()
    }

    private fun updateLastInput(inputField: TextField, lastInputLabel: Label) {
        val combo = extractComboList(inputField.text)

        fun validated(combo: List<String>) = when {
            combo[0] == "" -> "(Пустая команда)"
            actualCommand(combo[0]) == INVALID -> "${combo.joinToString(" ")} (Неправильная команда)"
            else -> combo.joinToString(" ")
        }

        lastInputLabel.text = "${validated(combo)}\n${validated(lastInput.split(" "))}"
        lastInput = combo.joinToString(" ")
    }

    private fun extractComboList(input: String) = input
            .trim().replace(Regex("\\s+"), " ").split(" ")

    private fun make(action: Action) {

    }

    fun actualText(): String {
 //       locations.find {
   //         it.key == character.location
     //   }!!.sections.find {
       //      it.key == character.section
       // }!!.descriptions.
        return ""
    }
}