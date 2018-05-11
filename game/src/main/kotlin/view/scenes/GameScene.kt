package view.scenes

import javafx.scene.control.Label
import javafx.scene.control.TextArea
import javafx.scene.control.TextField
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyCode.DOWN
import javafx.scene.input.KeyCode.ENTER
import javafx.scene.input.KeyCodeCombination
import javafx.scene.input.KeyCombination.SHIFT_DOWN
import javafx.scene.input.KeyEvent
import javafx.scene.text.Font.font
import model.Settings.LARGE
import model.Settings.MEDIUM
import model.Settings.SMALL
import model.Settings.fontSize

object GameScene : Scene() {
    private var lastInput: String = ""

    fun handle(code: KeyCode, inputField: TextField, lastInputLabel: Label, outputField: TextArea) {
        when (code) {
            ENTER -> {
                lastInputLabel.text = "${inputField.text}\n$lastInput"
                lastInput = inputField.text
                inputField.text = ""
            }
        }
    }

    fun handle(event: KeyEvent, inputField: TextField, lastInputLabel: Label, outputField: TextArea) {
        when {
            KeyCodeCombination(DOWN, SHIFT_DOWN).match(event) -> {
                if (lastInput != "") {
                    inputField.text = lastInput
                    inputField.requestFocus()
                    inputField.end()
                }
            }
        }
    }

    private fun handle(command: String) {

    }

    fun update(outputField: TextArea, inputField: TextField, lastInput: Label) {
        outputField.font = font(fontSize())
        inputField.font = font(fontSize())
        lastInput.font = font(fontSize())

        when (fontSize()) {
            SMALL -> lastInput.style = "$lastInputStyle -fx-label-padding: 10 0 0 10;"
            MEDIUM -> lastInput.style = "$lastInputStyle -fx-label-padding: 7 0 0 10;"
            LARGE -> lastInput.style = "$lastInputStyle -fx-label-padding: 5 0 0 10;"
        }
    }
}