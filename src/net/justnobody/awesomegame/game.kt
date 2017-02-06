package net.justnobody.awesomegame

import javafx.animation.AnimationTimer
import javafx.application.Application
import javafx.event.EventHandler
import javafx.scene.Group
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.scene.canvas.GraphicsContext
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import javafx.scene.paint.Color
import javafx.stage.Stage

const val SCREEN_WIDTH = 800.0
const val SCREEN_HEIGHT = 600.0
const val GAME_TITLE = "awesome game"

val BACKGROUND_COLOR = Color(0.2, 0.2, 0.3, 1.0)

class GameApp : Application() {
  override fun start(window: Stage) {
    val game = Game(window)

    val root = Group()
    val scene = Scene(root)

    scene.onKeyPressed = EventHandler<KeyEvent> {
      when (it.code) {
        KeyCode.ESCAPE -> window.close()
        else -> game.handleKeyPress(it)
      }
    }

    scene.onKeyReleased = EventHandler<KeyEvent> { game.handleKeyRelease(it) }

    val canvas = Canvas(SCREEN_WIDTH, SCREEN_HEIGHT)
    root.children.add(canvas)

    val timer = GameTimer(game, canvas.graphicsContext2D)
    timer.start()

    window.title = GAME_TITLE
    window.scene = scene
    window.show()
  }
}

class GameTimer(val game: Game, val gc: GraphicsContext) : AnimationTimer() {
  var time = System.nanoTime()

  override fun handle(now: Long) {
    val delta = (now - time) / 1000000000.0
    time = now
    game.update(delta)
    game.draw(gc)
  }
}

class Game(val window: Stage) {
  val player = Player()

  fun update(dt: Double) {
    player.update(dt)
  }

  fun draw(gc: GraphicsContext) {
    gc.fill = BACKGROUND_COLOR
    gc.fillRect(0.0, 0.0, SCREEN_WIDTH, SCREEN_HEIGHT)
    player.draw(gc)
  }

  fun handleKeyPress(event: KeyEvent) {
    when (event.code) {
      KeyCode.ESCAPE -> window.close()
      KeyCode.LEFT, KeyCode.A -> player.movingLeft = true
      KeyCode.RIGHT, KeyCode.D -> player.movingRight = true
      else -> {}
    }
  }

  fun handleKeyRelease(event: KeyEvent) {
    when (event.code) {
      KeyCode.LEFT, KeyCode.A -> player.movingLeft = false
      KeyCode.RIGHT, KeyCode.D -> player.movingRight = false
      else -> {}
    }
  }
}
