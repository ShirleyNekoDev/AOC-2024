import utils.Direction4
import utils.Point

object Day06 {
    const val OBSTACLE_CHAR = '#'
    const val START_CHAR = '^'

    private data class GuardMap(
        val obstacles: Set<Point>,
        val start: Point,
        val width: Int,
        val height: Int,
    ) {
        companion object {
            fun parse(input: List<String>) : GuardMap {
                lateinit var start: Point
                val obstacles = buildSet<Point> {
                    input.forEachIndexed { y, line ->
                        line.forEachIndexed { x, char ->
                            when(char) {
                                OBSTACLE_CHAR -> add(Point(x, y))
                                START_CHAR -> start = Point(x, y)
                            }
                        }
                    }
                }
                val width = input[0].length
                val height = input.size
                return GuardMap(obstacles, start, width, height)
            }
        }
    }

    private fun nextPosition(position: Point, direction: Direction4): Point {
        return position.copy(
            x = position.x + direction.dx,
            y = position.y + direction.dy,
        )
    }

    private data class GuardState(
        val position: Point,
        val direction: Direction4,
        // visited fields with direction which we faced on the field
        // we use linked maps/sets to track insertion ordering
        val visitedFields: LinkedHashMap<Point, LinkedHashSet<Direction4>> = LinkedHashMap<Point, LinkedHashSet<Direction4>>(),
    )

    private enum class WalkTerminationReason {
        LOOP,
        BOUNDS,
    }

    private fun walkState(startState: GuardState, guardMap: GuardMap): WalkTerminationReason {
        var state = startState
        while(true) {
            // add current position+direction to visited
            state.visitedFields
                .getOrPut(state.position) { LinkedHashSet() }
                .add(state.direction)

            val nextPosition = nextPosition(state.position, state.direction)
            if(nextPosition in guardMap.obstacles) {
                // turn right
                val newDirection = when(state.direction) {
                    Direction4.N -> Direction4.E
                    Direction4.E -> Direction4.S
                    Direction4.S -> Direction4.W
                    Direction4.W -> Direction4.N
                }
                state = state.copy(
                    direction = newDirection,
                    position = nextPosition(state.position, newDirection),
                )
            } else {
                // walk straight
                state = state.copy(
                    position = nextPosition
                )
            }

            if(nextPosition.x < 0 || nextPosition.x >= guardMap.width || nextPosition.y < 0 || nextPosition.y >= guardMap.height)
                return WalkTerminationReason.BOUNDS // out of bounds
            if(state.position in state.visitedFields) {
                // already visited this field (but we could be crossin it from a different direction)
                if(state.direction in state.visitedFields[state.position]!!) {
                    // now we are definitively in a loop
                    return WalkTerminationReason.LOOP
                }
            }
        }
    }

    private fun findVisitedFields(guardMap: GuardMap): List<Point> {
        var state = GuardState(
            position = guardMap.start,
            direction = Direction4.N, // always faces upwards at start
        )
        walkState(state, guardMap)
        return state.visitedFields.keys.toList()
    }

    fun part1(input: List<String>): Int = input
        .let(GuardMap::parse)
        .let(::findVisitedFields)
        .size

    private fun findPossibleLoopingObstaclePlacements(guardMap: GuardMap): List<Point> {
        var state = GuardState(
            position = guardMap.start,
            direction = Direction4.N, // always faces upwards at start
        )
        // do initial walk to generate all possible positions for the obstacle
        walkState(state, guardMap)
        val guardPath = state.visitedFields
        TODO()
    }

    fun part2(input: List<String>): Int = input
        .let(GuardMap::parse)
        .let(::findPossibleLoopingObstaclePlacements)
        .size
}
