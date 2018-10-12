package controller

import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.input.MouseButton
import javafx.scene.layout.Pane
import view.ControlledScreen
import view.Game
import view.ScreensController
import view.scenes.SavesScene.anySelected
import view.scenes.SavesScene.click
import view.scenes.SavesScene.enter
import view.scenes.SavesScene.exit
import view.scenes.SavesScene.press
import view.scenes.SavesScene.select
import view.scenes.SavesScene.unselect

class SavesController : ControlledScreen {
    private lateinit var myController: ScreensController

    lateinit var backButton: Button
    lateinit var saveParenPanel: Pane
    lateinit var firstSavePanel: Pane
    lateinit var firstEmptyLabel: Label
    lateinit var secondSavePanel: Pane
    lateinit var secondEmptyLabel: Label
    lateinit var thirdSavePanel: Pane
    lateinit var thirdEmptyLabel: Label
    lateinit var fourthSavePanel: Pane
    lateinit var fourthEmptyLabel: Label
    lateinit var atosaveParentPanel: Pane
    lateinit var autosavePanel: Pane
    lateinit var autosaveEmptyLabel: Label

    override fun setScreenParent(screenPage: ScreensController) {
        myController = screenPage
    }

    fun initialize() {
        firstSavePanel.setOnMouseEntered { enter(firstSavePanel) }
        firstSavePanel.setOnMouseExited { exit(firstSavePanel) }
        firstSavePanel.setOnMousePressed { selectFirst() }
        firstSavePanel.setOnMouseClicked({
            if (it.button == MouseButton.PRIMARY)
                if (it.clickCount == 2)
                    selectFirst()
        })

        secondSavePanel.setOnMouseEntered { enter(secondSavePanel) }
        secondSavePanel.setOnMouseExited { exit(secondSavePanel) }
        secondSavePanel.setOnMousePressed { selectSecond() }
        secondSavePanel.setOnMouseClicked({
            if (it.button == MouseButton.PRIMARY)
                if (it.clickCount == 2)
                    selectSecond()
        })

        thirdSavePanel.setOnMouseEntered { enter(thirdSavePanel) }
        thirdSavePanel.setOnMouseExited { exit(thirdSavePanel) }
        thirdSavePanel.setOnMousePressed { selectThird() }
        thirdSavePanel.setOnMouseClicked({
            if (it.button == MouseButton.PRIMARY)
                if (it.clickCount == 2)
                    selectThird()
        })

        fourthSavePanel.setOnMouseEntered { enter(fourthSavePanel) }
        fourthSavePanel.setOnMouseExited { exit(fourthSavePanel) }
        fourthSavePanel.setOnMousePressed { selectFourth() }
        fourthSavePanel.setOnMouseClicked({
            if (it.button == MouseButton.PRIMARY)
                if (it.clickCount == 2)
                    selectFourth()
        })

        backButton.setOnAction { backAction() }
        backButton.setOnMouseClicked { click(backButton) }
        backButton.setOnMouseEntered { enter(backButton) }
        backButton.setOnMouseExited { exit(backButton) }
        backButton.setOnMousePressed { press(backButton) }
    }

    private fun selectFirst() {
        select(firstSavePanel)
        unselect(secondSavePanel, thirdSavePanel, fourthSavePanel)
    }

    private fun selectSecond() {
        select(secondSavePanel)
        unselect(firstSavePanel, thirdSavePanel, fourthSavePanel)
    }

    private fun selectThird() {
        select(thirdSavePanel)
        unselect(firstSavePanel, secondSavePanel, fourthSavePanel)
    }

    private fun selectFourth() {
        select(fourthSavePanel)
        unselect(firstSavePanel, secondSavePanel, thirdSavePanel)
    }

    private fun backAction() {
        myController.setScreen(Game.lastId)

        if (anySelected())
            unselect(firstSavePanel, secondSavePanel, thirdSavePanel, fourthSavePanel)
    }
}
