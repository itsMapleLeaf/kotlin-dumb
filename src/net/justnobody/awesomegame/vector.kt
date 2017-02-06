package net.justnobody.awesomegame

class Vector(val x: Double, val y: Double) {
  operator fun plus(other: Vector) = Vector(x + other.x, y + other.y)

  operator fun minus(other: Vector) = Vector(x - other.x, y - other.y)

  operator fun times(other: Double) = Vector(x * other, y * other)
  operator fun times(other: Vector) = Vector(x * other.x, y * other.y)

  operator fun div(other: Double) = Vector(x / other, y / other)
  operator fun div(other: Vector) = Vector(x / other.x, y / other.y)

  operator fun unaryMinus() = Vector(-x, -y)

  fun distanceTo(other: Vector) = Math.sqrt(Math.pow(other.x - x, 2.0) + Math.pow(other.y - y, 2.0))
}