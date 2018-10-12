package controller

import javafx.scene.control.Button
import javafx.scene.control.Slider
import javafx.scene.control.TextField
import javafx.scene.control.ToggleButton
import model.Settings.autosave
import model.Settings.effectsVolume
import model.Settings.existChanges
import model.Settings.fontSize
import model.Settings.musicVolume
import model.Settings.read
import model.Settings.save
import model.Settings.setDefaults
import model.Settings.updateMusicVolume
import view.ControlledScreen
import view.Game
import view.ScreensController
import view.scenes.SettingsScene.click
import view.scenes.SettingsScene.enter
import view.scenes.SettingsScene.exit
import view.scenes.SettingsScene.press
import view.scenes.SettingsScene.selectedSettingToggle
import view.scenes.SettingsScene.selectedSettingToggleWithoutSound
import view.scenes.SettingsScene.switchToFirst
import view.scenes.SettingsScene.unselectedSettingToggle

class SettingsController : ControlledScreen {
    private lateinit var myController: ScreensController

    lateinit var smallFont: ToggleButton
    lateinit var mediumFont: ToggleButton
    lateinit var largeFont: ToggleButton
    lateinit var disableAutosave: ToggleButton
    lateinit var autosave15Min: ToggleButton
    lateinit var autosave30Min: ToggleButton
    lateinit var autosave1Hour: ToggleButton
    lateinit var effectsVolumeSlider: Slider
    lateinit var musicVolumeSlider: Slider
    lateinit var defaultsButton: Button
    lateinit var saveButton: Button
    lateinit var backButton: Button
    lateinit var effectsVolumeValue: TextField
    lateinit var musicVolumeValue: TextField

    fun initialize() {
        updateSettings()

        smallFont.setOnMousePressed {
            fontSize = "small"
            switchToFirst(smallFont, mediumFont, largeFont)
            checkChanges()
        }

        mediumFont.setOnMousePressed {
            fontSize = "medium"
            switchToFirst(mediumFont, smallFont, largeFont)
            checkChanges()
        }

        largeFont.setOnMousePressed {
            fontSize = "large"
            switchToFirst(largeFont, smallFont, mediumFont)
            checkChanges()
        }

        disableAutosave.setOnMousePressed {
            autosave = 0
            switchToFirst(disableAutosave, autosave15Min, autosave30Min, autosave1Hour)
            checkChanges()
        }

        autosave15Min.setOnMousePressed {
            autosave = 15
            switchToFirst(autosave15Min, disableAutosave, autosave30Min, autosave1Hour)
            checkChanges()
        }

        autosave30Min.setOnMousePressed {
            autosave = 30
            switchToFirst(autosave30Min, disableAutosave, autosave15Min, autosave1Hour)
            checkChanges()
        }

        autosave1Hour.setOnMousePressed {
            autosave = 60
            switchToFirst(autosave1Hour, disableAutosave, autosave15Min, autosave30Min)
            checkChanges()
        }

        effectsVolumeSlider.valueProperty().addListener { _ ->
            effectsVolume = effectsVolumeSlider.value / 100
            effectsVolumeValue.text = "${effectsVolumeSlider.value.toInt()}%"
            checkChanges()
        }

        musicVolumeSlider.valueProperty().addListener { _ ->
            musicVolume = musicVolumeSlider.value / 100
            musicVolumeValue.text = "${musicVolumeSlider.value.toInt()}%"
            checkChanges()
        }

        defaultsButton.setOnMouseEntered { enter(defaultsButton) }
        defaultsButton.setOnMouseExited { exit(defaultsButton) }
        defaultsButton.setOnMousePressed { onDefaultsPress() }
        defaultsButton.setOnMouseClicked { click(defaultsButton) }

        saveButton.setOnMouseEntered { enter(saveButton) }
        saveButton.setOnMouseExited { exit(saveButton) }
        saveButton.setOnMousePressed { onSavePress() }
        saveButton.setOnMouseClicked { onSaveClick() }

        backButton.setOnMouseEntered { enter(backButton) }
        backButton.setOnMouseExited { exit(backButton) }
        backButton.setOnMousePressed { onBackPress() }
        backButton.setOnMouseClicked { click(backButton) }
        backButton.setOnAction { goBackToMenu() }
    }

    override fun setScreenParent(screenPage: ScreensController) {
        myController = screenPage
    }

    private fun checkChanges() {
        if (existChanges)
            saveButton.isDisable = false
    }

    private fun onDefaultsPress() {
        setDefaults()
        updateSettings()
        press(defaultsButton)
        checkChanges()
    }

    private fun onSaveClick() {
        click(saveButton)
        saveButton.isDisable = true
    }

    private fun onSavePress() {
        save()
        press(saveButton)
    }

    private fun goBackToMenu() {
        Game.lastId = Game.settingsWindow
        myController.setScreen(Game.mainMenuWindow)
        saveButton.isDisable = true
    }

    private fun onBackPress() {
        read()
        updateSettings()
        press(backButton)
    }

    private fun updateSettings() {
        if (musicVolume != 1.0) {
            when {
                musicVolume > 1.0 -> {
                    musicVolumeSlider.value = 100.0
                    musicVolumeValue.text = "100%"
                }
                musicVolume < 0 -> {
                    musicVolumeSlider.value = 0.0
                    musicVolumeValue.text = "0%"
                }
                else -> {
                    musicVolumeSlider.value = musicVolume * 100
                    musicVolumeValue.text = "${musicVolumeSlider.value.toInt()}%"
                }
            }
        } else {
            musicVolumeSlider.value = 100.0
            musicVolumeValue.text = "${musicVolumeSlider.value.toInt()}%"
        }

        if (effectsVolume != 1.0) {
            when {
                effectsVolume > 1.0 -> {
                    effectsVolumeSlider.value = 100.0
                    effectsVolumeValue.text = "100%"
                }
                effectsVolume < 0 -> {
                    effectsVolumeSlider.value = 0.0
                    effectsVolumeValue.text = "0%"
                }
                else -> {
                    effectsVolumeSlider.value = effectsVolume * 100
                    effectsVolumeValue.text = "${effectsVolumeSlider.value.toInt()}%"
                }
            }
        } else {
            effectsVolumeSlider.value = 100.0
            effectsVolumeValue.text = "${effectsVolumeSlider.value.toInt()}%"
        }

        when (fontSize) {
            "small" -> {
                selectedSettingToggleWithoutSound(smallFont)
                unselectedSettingToggle(mediumFont, largeFont)
            }
            "medium" -> {
                selectedSettingToggleWithoutSound(mediumFont)
                unselectedSettingToggle(smallFont, largeFont)
            }
            "large" -> {
                selectedSettingToggleWithoutSound(largeFont)
                unselectedSettingToggle(smallFont, mediumFont)
            }
        }

        when (autosave) {
            0 -> {
                selectedSettingToggleWithoutSound(disableAutosave)
                unselectedSettingToggle(autosave15Min, autosave30Min, autosave1Hour)
            }
            15 -> {
                selectedSettingToggleWithoutSound(autosave15Min)
                unselectedSettingToggle(disableAutosave, autosave30Min, autosave1Hour)
            }
            30 -> {
                selectedSettingToggleWithoutSound(autosave30Min)
                unselectedSettingToggle(disableAutosave, autosave15Min, autosave1Hour)
            }
            60 -> {
                selectedSettingToggleWithoutSound(autosave1Hour)
                unselectedSettingToggle(disableAutosave, autosave15Min, autosave30Min)
            }
        }

    }
}
