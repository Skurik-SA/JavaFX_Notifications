package Notify

import javafx.animation.*
import javafx.concurrent.Task
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.ComboBox
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.image.Image
import javafx.scene.input.MouseEvent
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.scene.paint.ImagePattern
import javafx.scene.text.Font
import javafx.stage.Screen
import javafx.stage.Stage
import javafx.stage.StageStyle
import javafx.util.Duration
import java.io.File
import javafx.collections.FXCollections

import javafx.scene.shape.*

import javafx.animation.PathTransition
import javafx.scene.media.AudioClip

import javafx.scene.shape.MoveTo
import java.nio.file.Paths
import javafx.animation.KeyFrame

import javafx.animation.Timeline





class Notify{

    var content = HBox()

    enum class Position {
        RIGHT_BOTTOM,
        RIGHT_TOP,
        LEFT_BOTTOM,
        LEFT_TOP
    }

    enum class Border {
        SQUARE,
        CIRCLE
    }

    class Config(
        val mode: Int?,
        val defWwidth: Double?,
        val defHeight: Double?,
        val pos: Position?,
        val title: String?,
        val msg: String?,
        val appName: String?,
        val iconBorder: Border?,
        val iconPath: String?,
        val textColor: String?,
        val bgColor: String?,
        val bgOpacity: Double?,
        val waitTime: Int?,
        val sound: AudioClip?
    ) {

        data class Builder(
            var mode: Int? = null,
            var defWwidth: Double? = null,
            var defHeight: Double? = null,
            var pos: Position? = null,
            var title: String? = null,
            var msg: String? = null,
            var appName: String? = null,
            var iconBorder: Border? = null,
            var iconPath: String? = null,
            var textColor: String? = null,
            var bgColor: String? = null,
            var bgOpacity: Double? = null,
            var waitTime: Int? = null,
            var sound: AudioClip? = null,
        )
        {
            fun mode(mode: Int) = apply { this.mode = mode }
            fun defWwidth(defWwidth: Double) = apply { this.defWwidth = defWwidth }
            fun defHeight(defHeight: Double) = apply { this.defHeight = defHeight }
            fun pos(pos: Position) = apply { this.pos = pos }
            fun title(title: String) = apply { this.title = title }
            fun msg(msg: String) = apply { this.msg = msg }
            fun appName(appName: String) = apply { this.appName = appName }
            fun iconBorder(iconBorder: Border) = apply { this.iconBorder = iconBorder }
            fun iconPath(iconPath: String) = apply { this.iconPath = iconPath }
            fun textColor(textColor: String) = apply { this.textColor = textColor }
            fun bgColor(bgColor: String) = apply { this.bgColor = bgColor }
            fun bgOpacity(bgOpacity: Double) = apply { this.bgOpacity = bgOpacity }
            fun waitTime(waitTime: Int) = apply { this.waitTime = waitTime }
            fun sound(sound: AudioClip) = apply { this.sound = sound }
            fun build() = Config(mode, defWwidth, defHeight, pos, title, msg, appName, iconBorder, iconPath, textColor, bgColor, bgOpacity, waitTime, sound)
        }
}
    var config = Config.Builder()
        .mode(0)
        .defWwidth(500.0)
        .defHeight(200.0)
        .pos(Position.LEFT_TOP)
        .title("TITLE")
        .msg("MESSAGE")
        .appName( "app_name")
        .iconBorder(Border.SQUARE)
        .iconPath("https://softboxmarket.com/images/thumbnails/618/540/detailed/3/official-bts-wings-2nd-album-cd-poster-po_00.jpg")
        .textColor("#FFFFFF")
        .bgColor("#000000")
        .bgOpacity(1.0)
        .waitTime(5000)
        .sound(AudioClip(Paths.get("src/main/resources/btn.mp3").toUri().toString()))


    var popup = Stage()

    fun startNotify()
    {
        start()
    }

    private fun start() {
        build()

        var screenRect = Screen.getPrimary().bounds

        var shift = 10.0

        when (config.pos) {
            Position.LEFT_BOTTOM -> {
                popup.x = shift
                popup.y = screenRect.height - config.defHeight!! - shift
            }
            Position.LEFT_TOP -> {
                popup.x = shift
                popup.y = shift
            }
            Position.RIGHT_BOTTOM -> {
                popup.x = screenRect.width - config.defWwidth!! - shift
                popup.y = screenRect.height - config.defHeight!! - shift
            }
            Position.RIGHT_TOP -> {
                popup.x = screenRect.width - config.defWwidth!! - shift
                popup.y = shift
            }
        }

        val task = object: Task<Void>() {
            @Throws(InterruptedException::class)
            override fun call(): Void? {
                Thread.sleep(config.waitTime!!.toLong())
                when (config.mode)
                {
                    0 -> { cloaseAnim() }

                    1 -> { closeAnimpt() }

                    2 -> { closeAnimpt() }

                    3 -> { closeAnimpt() }

                }
                return null
            }
        }
        Thread(task).start()

        if (config.mode == 0) {
            popup.addEventFilter(MouseEvent.MOUSE_PRESSED, EventHandler { me: MouseEvent ->
                cloaseAnim()
            })
        }
        popup.scene = Scene(content)
//        popup.initOwner(primaryStage)
        popup.initStyle(StageStyle.TRANSPARENT)
        popup.opacity = config.bgOpacity!!
        popup.show()

        when (config.mode)
        {
            0 -> { openAnim() }

            1 -> { openAnimpt() }

            2 -> { openAnimpt() }

            3 -> { openAnimpt() }

        }

    }

//    private fun openAnimTL() {
//        val timeline = Timeline()
//        timeline.cycleCount = Timeline.INDEFINITE
//        timeline.isAutoReverse = true
//        val kv = KeyValue(popup.x, 300.0)
//        val kf = KeyFrame(Duration.millis(500.0), kv)
//        timeline.keyFrames.add(kf)
//        timeline.play()
//    }
//
//    private fun closeAnimTL() {
//
//    }

    private fun openAnimpt() {
        val ft = FadeTransition(Duration.millis(1000.0), content)
        ft.fromValue = 0.0
        ft.toValue = config.bgOpacity!!
        ft.cycleCount = 1
        ft.setOnFinished {
            println("open")
        }
        ft.play()


        var screenRect = Screen.getPrimary().bounds
        var shift = 5.0

        val path = Path()

        when(config.pos)
        {
            Position.LEFT_TOP -> {
                path.elements.add(MoveTo(config.defWwidth!! * -0.5, config.defHeight!! * 0.5 + shift))
                path.elements.add(LineTo(config.defWwidth!! * 0.5, config.defHeight!! * 0.5 + shift))
                println(config.defHeight!!)
                println(config.defWwidth!!)
            }
            Position.RIGHT_TOP -> {
                path.elements.add(MoveTo(screenRect.width - config.defWwidth!! * 0.5, config.defHeight!! * 0.5 + shift))
                path.elements.add(LineTo(config.defWwidth!! * 0.5, config.defHeight!! * 0.5 + shift))
                println(config.defHeight!!)
                println(config.defWwidth!!)
            }
            Position.LEFT_BOTTOM -> {
                path.elements.add(MoveTo(config.defWwidth!! * -0.5, config.defHeight!! * 0.5 - shift))
                path.elements.add(LineTo(config.defWwidth!! * 0.5, config.defHeight!! * 0.5 - shift))
            }
            Position.RIGHT_BOTTOM -> {
                path.elements.add(MoveTo(screenRect.width - config.defWwidth!! * 0.5, config.defHeight!! * 0.5 ))
                path.elements.add(LineTo(config.defWwidth!! * 0.5, config.defHeight!! * 0.5 ))
            }
        }

        val pathTransition = PathTransition()
        pathTransition.duration = Duration.millis(700.0)
        pathTransition.path = path
        pathTransition.node = content
        pathTransition.orientation = PathTransition.OrientationType.NONE
        pathTransition.cycleCount = 1
        pathTransition.play()

    }

    private fun closeAnimpt() {
        val ft = FadeTransition(Duration.millis(700.0), content)
        ft.fromValue = config.bgOpacity!!
        ft.toValue = 0.0
        ft.cycleCount = 1
        ft.setOnFinished {
            println("close")
            popup.close()
        }
        ft.play()

        var screenRect = Screen.getPrimary().bounds
        var shift = 5.0

        val path = Path()

        when(config.pos)
        {
            Position.LEFT_TOP -> {
                path.elements.add(MoveTo(config.defWwidth!! * 0.5, config.defHeight!! * 0.5 + shift))
                path.elements.add(LineTo(config.defWwidth!! * -0.5, config.defHeight!! * 0.5 + shift))
            }
            Position.RIGHT_TOP -> {
                path.elements.add(MoveTo(config.defWwidth!! * 0.5, config.defHeight!! * 0.5 + shift))
                path.elements.add(LineTo(screenRect.width - config.defWwidth!! * 0.5 , config.defHeight!! * 0.5 + shift))
            }
            Position.LEFT_BOTTOM -> {
                path.elements.add(MoveTo(config.defWwidth!! * 0.5, config.defHeight!! * 0.5))
                path.elements.add(LineTo(config.defWwidth!! * -0.5, config.defHeight!! * 0.5))
            }
            Position.RIGHT_BOTTOM -> {
                path.elements.add(MoveTo(config.defWwidth!! * 0.5, config.defHeight!! * 0.5))
                path.elements.add(LineTo(screenRect.width - config.defWwidth!! * 0.5, config.defHeight!! * 0.5))
            }
        }


        val pathTransition = PathTransition()
        pathTransition.duration = Duration.millis(800.0)
        pathTransition.path = path
        pathTransition.node = content
        pathTransition.orientation = PathTransition.OrientationType.NONE
        pathTransition.cycleCount = 1
        pathTransition.play()

        config.sound!!.play()


    }

    private fun openAnim() {
        val ft = FadeTransition(Duration.millis(1500.0), content)
        ft.fromValue = 0.0
        ft.toValue = config.bgOpacity!!
        ft.cycleCount = 1
        ft.setOnFinished {
            println("open")
        }
        ft.play()
    }

    private fun cloaseAnim() {
        val ft = FadeTransition(Duration.millis(1500.0), content)
        ft.fromValue = config.bgOpacity!!
        ft.toValue = 0.0
        ft.cycleCount = 1
        ft.setOnFinished {
            println("close")
            popup.close()
        }
//        println("close")
        ft.play()
    }

    private fun build() {
        content.setPrefSize(config.defWwidth!!, config.defHeight!!)
        content.setPadding(Insets(5.0, 5.0, 5.0, 5.0))
        content.spacing = 10.0
        content.style = "-fx-background-color:" + config.bgColor

        var path = config.iconPath
        if (!config.iconPath!!.isEmpty()) {
            if (config.iconPath!!.substring(0, 4) != "http") {
                path = File(config.iconPath).toURI().toURL().toString();
            }

            var icoBorder = if (config.iconBorder == Border.CIRCLE) {
                Circle(config.defHeight!! / 2 , config.defHeight!! /2 , config.defHeight!! / 2)
            } else {
                Rectangle(config.defHeight!! / 2 , config.defHeight!! /2, config.defHeight!!, config.defHeight!!)
            }
            icoBorder.setFill(ImagePattern(Image(path)))
            content.children.add(icoBorder)
        }

        var msgLayout = VBox()

        var title = Label(config.title)
        title.font = Font(24.0)
        title.style = "-fx-font-weight: bold; -fx-text-fill:"+config.textColor

        var message = Label(config.msg)
        message.font = Font(20.0)
        message.style = "-fx-text-fill:"+config.textColor

        var app = Label(config.appName)
        app.font = Font(16.0)
        app.style = "-fx-text-fill:"+config.textColor

        when (config.mode)
        {
            0 -> {

                msgLayout.children.addAll(title, message, app)
                content.children.add(msgLayout)
            }

            1 -> {
                var btnOK = Button()
                btnOK.text = "OK"
                btnOK.onAction = EventHandler {
                    println("OK")
                    closeAnimpt()
                }

                var btnCANCEL = Button()
                btnCANCEL.text = "Cancel"
                btnCANCEL.onAction = EventHandler {
                    println("CANCEL")
                    closeAnimpt()
                }

                var ButtonLayout = HBox()

                ButtonLayout.children.addAll(btnOK, btnCANCEL)
                msgLayout.children.addAll(title, message, app, ButtonLayout)
                content.children.add(msgLayout)
            }

            2 -> {
                var btnOK = Button()
                btnOK.text = "OK"
                btnOK.onAction = EventHandler {
                    println("OK")
                    closeAnimpt()
                }

                var btnCANCEL = Button()
                btnCANCEL.text = "Cancel"
                btnCANCEL.onAction = EventHandler {
                    println("CANCEL")
                    closeAnimpt()
                }

                var ButtonLayout = HBox()

                var textField = TextField()
                ButtonLayout.children.addAll(btnOK, btnCANCEL)
                msgLayout.children.addAll(title, message, app, textField, ButtonLayout)
                content.children.add(msgLayout)
            }

            3 -> {
                var btnOK = Button()
                btnOK.text = "OK"
                btnOK.onAction = EventHandler {
                    println("OK")
                    closeAnimpt()
                }

                var btnCANCEL = Button()
                btnCANCEL.text = "Cancel"
                btnCANCEL.onAction = EventHandler {
                    println("CANCEL")
                    closeAnimpt()
                }

                var ButtonLayout = HBox()

                val options = FXCollections.observableArrayList(
                    "Play",
                    "Close",
                    "Open x"
                )
                val msgcomboBox = ComboBox(options)
                msgcomboBox.promptText = "Combo"

                ButtonLayout.children.addAll(btnOK, btnCANCEL)
                msgLayout.children.addAll(title, message, app, ButtonLayout, msgcomboBox)
                content.children.add(msgLayout)
            }
        }

    }

}