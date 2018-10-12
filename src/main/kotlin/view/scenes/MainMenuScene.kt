package view.scenes

import javafx.scene.media.Media
import javafx.scene.media.MediaPlayer
import javafx.scene.media.MediaPlayer.INDEFINITE
import model.Settings
import model.Settings.musicVolume
import view.audio.Track.MAIN_MENU_MUSIC

object MainMenuScene : Scene() {
    private val media: Media = Media(javaClass.getResource(MAIN_MENU_MUSIC.filePath).toString())
    private val mediaPlayer = MediaPlayer(media)

    init {
        mediaPlayer.volume = musicVolume
        mediaPlayer.cycleCount = INDEFINITE
        play()
    }

    fun play() {
        mediaPlayer.play()
    }

    fun pause() {
        mediaPlayer.pause()
    }

    fun stop() {
        mediaPlayer.stop()
    }

    fun volume(volume: Double = Settings.musicVolume) {
        mediaPlayer.volume = volume
    }
}