package controller

import view.ControlledScreen
import view.ScreensController

class LoadingController : ControlledScreen {
    private lateinit var myController: ScreensController

    override fun setScreenParent(screenPage: ScreensController) {
        myController = screenPage
    }

    fun initialize() {
    }
}
