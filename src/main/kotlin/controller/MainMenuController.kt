package controller

import javafx.scene.control.Button
import model.GameSession
import view.ControlledScreen
import view.Game
import view.ScreensController
import view.scenes.MainMenuScene
import view.scenes.MainMenuScene.click
import view.scenes.MainMenuScene.enter
import view.scenes.MainMenuScene.exit
import view.scenes.MainMenuScene.press

class MainMenuController : ControlledScreen {
    private lateinit var myController: ScreensController

    lateinit var newGameButton: Button
    lateinit var loadButton: Button
    lateinit var settingsButton: Button
    lateinit var exitButton: Button

    fun initialize() {
        MainMenuScene
        GameSession.selLocations()

        newGameButton.setOnMouseEntered { enter(newGameButton) }
        newGameButton.setOnMouseExited { exit(newGameButton) }
        newGameButton.setOnMousePressed { press(newGameButton) }
        newGameButton.setOnMouseClicked { click(newGameButton) }
        newGameButton.setOnAction { goTo(Game.gameWindow) }

        loadButton.setOnMouseEntered { enter(loadButton) }
        loadButton.setOnMouseExited { exit(loadButton) }
        loadButton.setOnMousePressed { press(loadButton) }
        loadButton.setOnMouseClicked { click(loadButton) }
        loadButton.setOnAction { goTo(Game.savesWindow) }

        settingsButton.setOnMouseEntered { enter(settingsButton) }
        settingsButton.setOnMouseExited { exit(settingsButton) }
        settingsButton.setOnMousePressed { press(settingsButton) }
        settingsButton.setOnMouseClicked { click(settingsButton) }
        settingsButton.setOnAction { goTo(Game.settingsWindow) }

        exitButton.setOnMouseEntered { enter(exitButton) }
        exitButton.setOnMouseExited { exit(exitButton) }
        exitButton.setOnMousePressed { press(exitButton) }
        exitButton.setOnMouseClicked { click(exitButton); System.exit(0) }
    }

    override fun setScreenParent(screenPage: ScreensController) {
        myController = screenPage
    }

    private fun goTo(screen: String) {
        Game.lastId = Game.mainMenuWindow
        myController.setScreen(screen)
    }
}
