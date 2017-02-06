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

class Game {
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

class GameTimer(val game: Game, val gc: GraphicsContext) : AnimationTimer() {
  var time = System.nanoTime()

  override fun handle(now: Long) {
    val delta = (now - time) / 1000000000.0
    time = now
    game.update(delta)
    game.draw(gc)
  }
}

class GameApp : Application() {
  override fun start(stage: Stage) {
    val game = Game()

    val root = Group()
    val scene = Scene(root)

    scene.onKeyPressed = EventHandler<KeyEvent> {
      when (it.code) {
        KeyCode.ESCAPE -> stage.close()
        else -> game.handleKeyPress(it)
      }
    }

    scene.onKeyReleased = EventHandler<KeyEvent> { game.handleKeyRelease(it) }

    val canvas = Canvas(SCREEN_WIDTH, SCREEN_HEIGHT)
    root.children.add(canvas)

    val timer = GameTimer(game, canvas.graphicsContext2D)
    timer.start()

    stage.title = GAME_TITLE
    stage.scene = scene
    stage.show()
  }
}
