package model

import controller.GameController
import view.scenes.GameScene
import view.scenes.MainMenuScene
import java.io.File

object Settings {
    const val SMALL = 14.0
    const val MEDIUM = 16.0
    const val LARGE = 18.0

    var effectsVolume = read("effectsVolume").toDouble()
        set(value) {
            if (field != value) {
                field = value
                existChanges = true
            }
        }

    var musicVolume = read("musicVolume").toDouble()
        set(value) {
            if (field != value) {
                field = value
                existChanges = true
                updateMusicVolume()
            }
        }

    var fontSize = read("fontSize")
        set(value) {
            if (field != value) {
                field = value
                existChanges = true
            }
        }

    var autosave = read("autosave").toInt()
        set(value) {
            if (field != value) {
                field = value
                existChanges = true
            }
        }

    var existChanges = false
        private set

    fun save() {
        File("src/main/resources/settings.txt").writeText(
                "effectsVolume=$effectsVolume\n" +
                        "musicVolume=$musicVolume\n" +
                        "fontSize=$fontSize\n" +
                        "autosave=$autosave")

        existChanges = false
    }

    fun read(value: String): String {
        var result: String? = null

        File("src/main/resources/settings.txt").forEachLine {
            if (value == it.split("=")[0])
                result = it.split("=")[1]
        }

        return result!!
    }

    fun read() {
        File("src/main/resources/settings.txt").forEachLine {
            when {
                it.startsWith("effectsVolume") -> effectsVolume = it.split("=")[1].toDouble()
                it.startsWith("musicVolume") -> musicVolume = it.split("=")[1].toDouble()
                it.startsWith("fontSize") -> fontSize = it.split("=")[1]
                it.startsWith("autosave") -> autosave = it.split("=")[1].toInt()
            }
        }
    }

    fun setDefaults() {
        effectsVolume = 1.0
        musicVolume = 1.0
        fontSize = "normal"
        autosave = 0
    }

    fun updateMusicVolume() {
        MainMenuScene.volume()
    }

    fun fontSize() = if (fontSize == "large") LARGE else if (fontSize == "medium") MEDIUM else SMALL
}