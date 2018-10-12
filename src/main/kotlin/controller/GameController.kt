package controller

import javafx.scene.control.Label
import javafx.scene.control.TextArea
import javafx.scene.control.TextField
import javafx.scene.input.KeyEvent
import view.ControlledScreen
import view.ScreensController
import view.scenes.GameScene

class GameController : ControlledScreen {
    private lateinit var myController: ScreensController

    lateinit var outputField: TextArea
    lateinit var inputField: TextField
    lateinit var lastInput: Label

    override fun setScreenParent(screenPage: ScreensController) {
        myController = screenPage
    }

    fun initialize() {
        GameScene.setActualDescription(outputField)
        GameScene.update(outputField, inputField, lastInput)

        inputField.addEventHandler(KeyEvent.KEY_RELEASED, {
            GameScene.handle(it!!, inputField, lastInput, outputField)
        })

        inputField.setOnKeyPressed {
            GameScene.handle(it.code, inputField, lastInput, outputField)
        }
    }
}
