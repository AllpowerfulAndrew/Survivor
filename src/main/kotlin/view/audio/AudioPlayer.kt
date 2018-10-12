package view.audio

import javafx.scene.media.AudioClip
import model.Settings

object AudioPlayer {

    fun play(track: Track, volume: Double = Settings.effectsVolume) {
        AudioClip(javaClass.getResource(track.filePath).toString()).play(volume)
    }
}