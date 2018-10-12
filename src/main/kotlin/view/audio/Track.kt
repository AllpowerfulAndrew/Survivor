package view.audio

enum class Track(val filePath: String) {
    // sounds
    ENTER_BUTTON_SOUND("../assets/audio/enter_button.wav"),
    PRESS_BUTTON_SOUND("../assets/audio/press_button.wav"),
    CLICK_BUTTON_SOUND("../assets/audio/click_button.wav"),
    SET_BUTTON_SOUND("../assets/audio/set_button.wav"),
    SAVE_SELECT_SOUND("../assets/audio/save_select.wav"),

    // music
    MAIN_MENU_MUSIC("../assets/audio/main_theme.mp3");
}