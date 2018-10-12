package view.scenes

import javafx.scene.control.ToggleButton
import view.audio.AudioPlayer.play
import view.audio.Track.SET_BUTTON_SOUND

object SettingsScene : Scene() {

    fun switchToFirst(selected: ToggleButton, vararg unselected: ToggleButton) {
        selectedSettingToggle(selected)

        for (button in unselected)
            unselectedSettingToggle(button)
    }

    fun selectedSettingToggle(button: ToggleButton) {
        play(SET_BUTTON_SOUND)
        selectedSettingToggleWithoutSound(button)
    }

    fun selectedSettingToggleWithoutSound(button: ToggleButton) {
        button.style = selectedToggleStyle
        button.textFill = selectedToggleTextFill
    }

    fun unselectedSettingToggle(vararg buttons: ToggleButton) {
        for (button in buttons) {
            button.style = unselectedToggleStyle
            button.textFill = unselectedToggleTextFill
        }
    }
}