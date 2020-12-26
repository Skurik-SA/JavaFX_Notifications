package GUISamples

import Notify.Notify
import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.layout.HBox
import javafx.stage.Stage
import Notify.*
import javafx.scene.media.AudioClip
import java.nio.file.Paths


class asd : Application() {

    override fun start(primaryStage: Stage?) {

        var temp = Notify()
        temp.config.mode = 3
        temp.config.pos = Notify.Position.RIGHT_BOTTOM
        temp.config.appName = "CyberPunk 2077"
        temp.config.msg = "Update downloaded!"
        temp.config.title = "Hey, User!"
        temp.startNotify()

    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Application.launch(asd::class.java)
        }
    }
}