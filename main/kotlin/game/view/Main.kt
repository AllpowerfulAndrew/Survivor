package game.view

import javafx.application.Application
import javafx.application.Application.launch
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage

class Main : Application() {
    override fun start(primaryStage: Stage) {
        val root: Parent = FXMLLoader.load(javaClass.getResource("elements.fxml"))
        primaryStage.title = "Survivor"
        primaryStage.isResizable = false

        val scene = Scene(root, 1100.0, 690.0)
        primaryStage.scene = scene
        scene.stylesheets.add(Main::class.java.getResource("styles.css").toExternalForm())
        primaryStage.show()
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
//            launch(Main::class.java)

            "asd ".split(" ").forEach {
                println(it.length)
            }
        }
    }
}
