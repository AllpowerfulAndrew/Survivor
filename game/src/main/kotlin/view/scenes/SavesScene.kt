package view.scenes

import javafx.scene.control.Label
import javafx.scene.layout.Pane
import view.audio.AudioPlayer.play
import view.audio.Track.SAVE_SELECT_SOUND

object SavesScene : Scene() {
    private var isEmptyFirst = true
    private var isEmptySecond = true
    private var isEmptyThird = true
    private var isEmptyFourth = true

    private var isSelectedFirst = false
    private var isSelectedSecond = false
    private var isSelectedThird = false
    private var isSelectedFourth = false

    fun select(pane: Pane) {
        if (!selected(pane) && !empty(pane)) {
            play(SAVE_SELECT_SOUND)

            pane.children.forEach {
                if (it.id.contains("EmptyLabel"))
                    (it as Label).textFill = selectedSavePaneEmptyLabelTextFill
            }

            pane.style = selectedSavePaneStyle

            when {
                pane.id.startsWith("first") -> isSelectedFirst = true
                pane.id.startsWith("second") -> isSelectedSecond = true
                pane.id.startsWith("third") -> isSelectedThird = true
                pane.id.startsWith("fourth") -> isSelectedFourth = true
            }
        }
    }

    fun unselect(vararg panes: Pane) {
        for (pane in panes) {
            pane.children.forEach {
                if (it.id.contains("EmptyLabel"))
                    (it as Label).textFill = unselectedSavePaneEmptyLabelTextFill
            }

            pane.style = unselectedSavePaneStyle

            when {
                pane.id.startsWith("first") -> isSelectedFirst = false
                pane.id.startsWith("second") -> isSelectedSecond = false
                pane.id.startsWith("third") -> isSelectedThird = false
                pane.id.startsWith("fourth") -> isSelectedFourth = false
            }
        }
    }

    fun enter(pane: Pane) {
        if (!selected(pane) && !empty(pane)) {
            pane.children.forEach {
                if (it.id.contains("EmptyLabel"))
                    (it as Label).textFill = enterSavePaneEmptyLabelTextFill
            }

            pane.style = enterSavePaneStyle
        }
    }

    fun exit(pane: Pane) {
        if (!selected(pane) && !empty(pane)) {
            pane.children.forEach {
                if (it.id.contains("Label"))
                    (it as Label).textFill = exitSavePaneEmptyLabelTextFill
            }

            pane.style = exitSavePaneStyle
        }
    }

    fun anySelected() = !isSelectedFirst && !isSelectedSecond && !isSelectedThird && !isSelectedFourth

    private fun selected(pane: Pane) = when {
        pane.id.startsWith("first") -> isSelectedFirst
        pane.id.startsWith("second") -> isSelectedSecond
        pane.id.startsWith("third") -> isSelectedThird
        pane.id.startsWith("fourth") -> isSelectedFourth
        else -> false
    }

    private fun empty(pane: Pane) = when {
        pane.id.startsWith("first") -> isEmptyFirst
        pane.id.startsWith("second") -> isEmptySecond
        pane.id.startsWith("third") -> isEmptyThird
        pane.id.startsWith("fourth") -> isEmptyFourth
        else -> true
    }
}
