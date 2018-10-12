package view.scenes

import javafx.scene.control.Button
import javafx.scene.paint.Paint.valueOf
import view.audio.AudioPlayer.play
import view.audio.Track.ENTER_BUTTON_SOUND
import view.audio.Track.PRESS_BUTTON_SOUND

abstract class Scene {
    protected val enterSavePaneStyle = "-fx-border-radius: 5; -fx-border-color: #aaaaaa; -fx-background-color: #28283e; -fx-background-radius: 5;"
    protected val exitSavePaneStyle = "-fx-border-radius: 5; -fx-border-color: #666666; -fx-background-color: #28283e; -fx-background-radius: 5;"
    protected val selectedSavePaneStyle = "-fx-background-color: #25273a; -fx-border-color: #997929; -fx-border-radius: 5; -fx-background-radius: 5;"
    protected val unselectedSavePaneStyle = "-fx-border-radius: 5; -fx-border-color: #666666; -fx-background-color: #28283e; -fx-background-radius: 5;"

    protected val enterSavePaneEmptyLabelTextFill = valueOf("#aaaaaa")!!
    protected val exitSavePaneEmptyLabelTextFill = valueOf("#666666")!!
    protected val selectedSavePaneEmptyLabelTextFill = valueOf("#997929")!!
    protected val unselectedSavePaneEmptyLabelTextFill = valueOf("#666666")!!

    protected val selectedToggleStyle = "-fx-background-color: #2E3348; -fx-border-color: #b2b2b2; -fx-border-radius: 5; -fx-background-radius: 5;"
    protected val unselectedToggleStyle = "-fx-background-color: #b2b2b2; -fx-border-radius: 5; -fx-background-radius: 5;"
    protected val selectedToggleTextFill = valueOf("#d1d2d3")!!
    protected val unselectedToggleTextFill = valueOf("#25273a")!!

    protected val inputFieldStyle = "-fx-text-fill: #aaaaaa; -fx-font-family: Consolas; -fx-background-color: #25273a; -fx-border-radius: 5; -fx-background-radius: 5; -fx-border-color: #aaaaaa;"
    protected val outputFieldStyle = "-fx-border-radius: 5; -fx-border-color: #aaaaaa; -fx-background-color: #25273a; -fx-background-radius: 5;"
    protected val lastInputStyle = "-fx-text-fill: #afada1; -fx-border-radius: 5; -fx-background-radius: 5; -fx-border-color: #aaaaaa; -fx-background-color: #25273a;"

    private val enterButtonStyle = "-fx-background-color: #25273a; -fx-border-color: #cca84e; -fx-border-radius: 5; -fx-background-radius: 5;"
    private val exitButtonStyle = "-fx-background-color: #2E3348; -fx-border-color: #aaaaaa; -fx-border-radius: 5; -fx-background-radius: 5;"
    private val pressButtonStyle = "-fx-background-color: #25273a; -fx-border-color: #997929; -fx-border-radius: 5; -fx-background-radius: 5;"
    private val clickButtonStyle = "-fx-background-color: #25273a; -fx-border-color: #cca84e; -fx-border-radius: 5; -fx-background-radius: 5;"

    private val enterButtonTextFill = valueOf("#cca84e")!!
    private val exitButtonTextFill = valueOf("#b2b2b2")!!
    private val pressButtonTextFill = valueOf("#997929")!!
    private val clickButtonTextFill = valueOf("#cca84e")!!

    fun enter(button: Button) {
        play(ENTER_BUTTON_SOUND)
        button.textFill = enterButtonTextFill
        button.style = enterButtonStyle
    }

    fun exit(button: Button) {
        button.textFill = exitButtonTextFill
        button.style = exitButtonStyle
    }

    fun press(button: Button) {
        play(PRESS_BUTTON_SOUND)
        button.textFill = pressButtonTextFill
        button.style = pressButtonStyle

    }

    fun click(button: Button) {
        button.textFill = clickButtonTextFill
        button.style = clickButtonStyle
    }
}