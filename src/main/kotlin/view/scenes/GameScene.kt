package view.scenes

import javafx.geometry.Insets
import javafx.scene.control.Label
import javafx.scene.control.TextArea
import javafx.scene.control.TextField
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyCode.DOWN
import javafx.scene.input.KeyCode.ENTER
import javafx.scene.input.KeyCodeCombination
import javafx.scene.input.KeyCombination.SHIFT_DOWN
import javafx.scene.input.KeyEvent
import javafx.scene.layout.*
import javafx.scene.paint.Paint
import javafx.scene.text.Font.font
import model.GameSession
import model.GameSession.handleAndUpdate
import model.Settings.LARGE
import model.Settings.MEDIUM
import model.Settings.SMALL
import model.Settings.fontSize

object GameScene : Scene() {
    fun handle(code: KeyCode, inputField: TextField, lastInputLabel: Label, outputField: TextArea) {
        when (code) {
            ENTER -> {
                lastInputLabel.border = updateLastInputBorder(inputField, lastInputLabel)
                handleAndUpdate(inputField, lastInputLabel, outputField)
            }
        }
    }

    fun handle(event: KeyEvent, inputField: TextField, lastInputLabel: Label, outputField: TextArea) {
        if (KeyCodeCombination(DOWN, SHIFT_DOWN).match(event)) GameSession.useLastInput(inputField)
    }

    fun update(outputField: TextArea, inputField: TextField, lastInput: Label) {
        outputField.font = font(fontSize())
        inputField.font = font(fontSize())
        lastInput.font = font(fontSize())

        when (fontSize()) {
            SMALL -> lastInput.padding = Insets(10.0, .0, .0, 10.0)
            MEDIUM -> lastInput.padding = Insets(7.0, .0, .0, 10.0)
            LARGE -> lastInput.padding = Insets(5.0, .0, .0, 10.0)
        }
    }

    private fun updateLastInputBorder(inputField: TextField, lastInputLabel: Label) = if (GameSession.valid(inputField.text))
        Border(BorderStroke(Paint.valueOf("#a1a1a1"), BorderStrokeStyle.SOLID, CornerRadii(5.0), BorderWidths.DEFAULT))
    else
        Border(BorderStroke(Paint.valueOf("#cc3300"), BorderStrokeStyle.SOLID, CornerRadii(5.0), BorderWidths.DEFAULT))

    fun setActualDescription(outputField: TextArea) {
        outputField.text = GameSession.actualText()
    }
}