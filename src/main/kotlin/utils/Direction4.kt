package utils

enum class Direction4(val dx: Int, val dy: Int) {
    N(0, -1),
    E(1, 0),
    S(0, 1),
    W(-1, 0),
}

fun Point.atDirection(direction: Direction4): Point = copy(
    x = x + direction.dx,
    y = y + direction.dy,
)
