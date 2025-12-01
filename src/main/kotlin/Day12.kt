import utils.Direction4
import utils.Position
import utils.atDirection
import java.util.ArrayDeque
import java.util.Deque
import kotlin.collections.flatMap
import kotlin.collections.takeWhile


typealias CommandList = Deque<Direction4>

object Day12 {
    const val WALL = '#'
    const val BOX = 'O'
    const val ROBOT = '@'

    class Warehouse(
        val map: MutableMap<Position, Obstacle>,
        var robot: Position,
        val commandList: CommandList,
    ) {
        enum class Obstacle {
            WALL, BOX;
        }

        companion object {
            fun parseInput(input: List<String>) : Warehouse {
                lateinit var robot: Position
                val mapInput = input.takeWhile { it.isNotEmpty() }
                return Warehouse(
                    map = buildMap {
                        mapInput.forEachIndexed { y, line ->
                            line.forEachIndexed { x, char ->
                                when(char) {
                                    WALL -> put(Position(x, y), Obstacle.WALL)
                                    BOX -> put(Position(x, y), Obstacle.BOX)
                                    ROBOT -> robot = Position(x, y)
                                    else -> {/* do nothing */}
                                }
                            }
                        }
                    }.toMutableMap(),
                    robot = robot,
                    commandList = ArrayDeque(input.subList(mapInput.size+1, input.size)
                        .flatMap { line ->
                            line.map { char ->
                                when(char) {
                                    '<' -> Direction4.W
                                    '^' -> Direction4.N
                                    '>' -> Direction4.E
                                    'v' -> Direction4.S
                                    else -> error("unknown movement $char")
                                }
                            }
                        }
                    )
                )
            }
        }

        /**
         * @return false, if the box could not be moved
         */
        fun moveBox(position: Position, direction: Direction4): Boolean {
            assert(map[position] == Obstacle.BOX)
            val targetPosition = position.atDirection(direction)
            val canMove = when(map[targetPosition]) {
                Obstacle.WALL -> return false
                Obstacle.BOX -> moveBox(targetPosition, direction)
                else -> true
            }
            if(canMove) {
                map.put(targetPosition, map.remove(position)!!)
            }
            return canMove
        }

        /**
         * @return true, if the robot is not yet done
         */
        fun step(): Boolean {
            if(commandList.isEmpty())
                return false
            val direction = commandList.pollFirst()
            val targetPosition = robot.atDirection(direction)
            val obstacle = map[targetPosition]
            when(obstacle) {
                Obstacle.WALL -> return true
                Obstacle.BOX -> moveBox(targetPosition, direction)
                else -> {}
            }
            robot = targetPosition
//            println(this)
            return true
        }

        fun coordinateSum(): Long = map.keys.sumOf { position ->
            100L * position.y + position.x
        }

        override fun toString(): String = """
            Warehouse(
                robot=$robot,
                map=$map,
                commandList=$commandList
            )
        """.trimIndent()
    }

    fun part1(input: List<String>): Long {
        val data = Warehouse.parseInput(input)
//        println(data)
        while(data.step()) { /*step*/ }
        return data.coordinateSum()
    }

    fun part2(input: List<String>): Int = 1
}
