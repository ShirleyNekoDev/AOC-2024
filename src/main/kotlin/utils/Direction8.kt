package utils

enum class Direction8(val dx: Int, val dy: Int) {
    N(0, -1),
    NE(1, -1),
    E(1, 0),
    SE(1, 1),
    S(0, 1),
    SW(-1, 1),
    W(-1, 0),
    NW(-1, -1);
}

fun Point.atDirection(direction: Direction8): Point = copy(
    x = x + direction.dx,
    y = y + direction.dy,
)
