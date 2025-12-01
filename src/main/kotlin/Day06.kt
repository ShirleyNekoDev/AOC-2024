import utils.Direction4
import utils.Position

object Day06 {
    const val OBSTACLE_CHAR = '#'
    const val START_CHAR = '^'

    private data class GuardMap(
        val obstacles: Set<Position>,
        val start: Position,
        val width: Int,
        val height: Int,
    ) {
        companion object {
            fun parse(input: List<String>) : GuardMap {
                lateinit var start: Position
                val obstacles = buildSet<Position> {
                    input.forEachIndexed { y, line ->
                        line.forEachIndexed { x, char ->
                            when(char) {
                                OBSTACLE_CHAR -> add(Position(x, y))
                                START_CHAR -> start = Position(x, y)
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

    private fun nextPosition(position: Position, direction: Direction4): Position {
        return position.copy(
            x = position.x + direction.dx,
            y = position.y + direction.dy,
        )
    }

    private data class GuardState(
        val position: Position,
        val direction: Direction4,
        // visited fields with direction which we faced on the field
        // we use linked maps/sets to track insertion ordering
        val visitedFields: LinkedHashMap<Position, LinkedHashSet<Direction4>> = LinkedHashMap<Position, LinkedHashSet<Direction4>>(),
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

    private fun findVisitedFields(guardMap: GuardMap): List<Position> {
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

    private fun findPossibleLoopingObstaclePlacements(guardMap: GuardMap): List<Position> {
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
