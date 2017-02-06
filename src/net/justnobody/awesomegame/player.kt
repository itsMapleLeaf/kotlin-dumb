package net.justnobody.awesomegame

import javafx.scene.canvas.GraphicsContext
import javafx.scene.paint.Color

class Player {
  var pos = Vector(100.0, 100.0)
  var movingLeft = false
  var movingRight = false

  fun update(dt: Double) {
    if (movingLeft) pos -= Vector(400 * dt, 0.0)
    if (movingRight) pos += Vector(400 * dt, 0.0)
  }

  fun draw(gc: GraphicsContext) {
    gc.fill = Color.WHITE
    gc.fillRect(pos.x, pos.y, 50.0, 50.0)
  }
}
