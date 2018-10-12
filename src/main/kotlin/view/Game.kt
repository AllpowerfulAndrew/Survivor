package view

import javafx.application.Application
import javafx.scene.Group
import javafx.scene.Scene
import javafx.stage.Stage

class Game : Application() {
    override fun start(primaryStage: Stage) {
        val mainContainer = ScreensController()
        mainContainer.loadScreen(Game.mainMenuWindow, Game.mainMenuFile)
        mainContainer.loadScreen(Game.loadingWindow, Game.loadingFile)
        mainContainer.loadScreen(Game.savesWindow, Game.savesFile)
        mainContainer.loadScreen(Game.settingsWindow, Game.settingsFile)
        mainContainer.loadScreen(Game.gameWindow, Game.gameFile)

        mainContainer.setScreen(Game.mainMenuWindow)

        val root = Group()
        root.children.addAll(mainContainer)
        val scene = Scene(root)

        primaryStage.title = "Выживший"
        primaryStage.isResizable = false
        primaryStage.scene = scene
        primaryStage.show()
    }

    companion object {
        const val mainMenuWindow = "main menu"
        const val mainMenuFile = "mainMenu.fxml"
        const val settingsWindow = "settings"
        const val settingsFile = "settings.fxml"
        const val savesWindow = "saves"
        const val savesFile = "saves.fxml"
        const val gameWindow = "game"
        const val gameFile = "game.fxml"
        const val loadingWindow = "loading"
        const val loadingFile = "loading.fxml"

        var lastId = mainMenuWindow

        @JvmStatic
        fun main(args: Array<String>) {
            launch(Game::class.java, *args)
        }
    }
}
