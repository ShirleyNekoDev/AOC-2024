import utils.Direction4
import utils.Point
import utils.atDirection


typealias Crop = Char

object Day12 {

    class Plot(
        val type: Crop,
        val id: Int,
        val positions: MutableSet<Point>,
    ) {
        fun connectionCount(pos: Point): Int =
            Direction4.entries.count { direction ->
                pos.atDirection(direction) in positions
            }

        val area: Int get() = positions.size

        val perimeter by lazy<Int> {
            positions.sumOf { pos -> 4 - connectionCount(pos) }
        }

        val sides by lazy<Int> {
            var sides = mutableSetOf<Pair<Direction4, Point>>()

            positions.forEach { pos ->
                if(pos.atDirection(Direction4.N) !in positions) {
                    var leftmostNPos = pos.atDirection(Direction4.W)
                    while(leftmostNPos in positions && leftmostNPos.atDirection(Direction4.N) !in positions) {
                        leftmostNPos = leftmostNPos.atDirection(Direction4.W)
                    }
                    sides.add(Direction4.N to leftmostNPos)
                }
                if(pos.atDirection(Direction4.S) !in positions) {
                    var leftmostSPos = pos.atDirection(Direction4.W)
                    while(leftmostSPos in positions && leftmostSPos.atDirection(Direction4.S) !in positions) {
                        leftmostSPos = leftmostSPos.atDirection(Direction4.W)
                    }
                    sides.add(Direction4.S to leftmostSPos)
                }
                if(pos.atDirection(Direction4.W) !in positions) {
                    var topmostWPos = pos.atDirection(Direction4.S)
                    while(topmostWPos in positions && topmostWPos.atDirection(Direction4.W) !in positions) {
                        topmostWPos = topmostWPos.atDirection(Direction4.S)
                    }
                    sides.add(Direction4.W to topmostWPos)
                }
                if(pos.atDirection(Direction4.E) !in positions) {
                    var topMostEPos = pos.atDirection(Direction4.S)
                    while(topMostEPos in positions && topMostEPos.atDirection(Direction4.E) !in positions) {
                        topMostEPos = topMostEPos.atDirection(Direction4.S)
                    }
                    sides.add(Direction4.E to topMostEPos)
                }
            }

            sides.size
        }

        override fun toString(): String = buildString {
            append("Plot[$type$id,area=$area,perimeter=$perimeter]{")
            positions.joinTo(this)
            append("}")
        }
    }

    fun List<String>.findPlots(): List<Plot> {
        val plotsByPos = mutableMapOf<Point, Plot>()
        val plotsByType = mutableMapOf<Crop, MutableList<Plot>>()

        forEachIndexed { y, line ->
            line.forEachIndexed { x, type ->
                val pos = Point(x, y)
                val plotW = plotsByPos[pos.atDirection(Direction4.W)]
                    ?.takeIf { it.type == type }
                    ?.apply { positions.add(pos) }
                val plotN = plotsByPos[pos.atDirection(Direction4.N)]
                    ?.takeIf { it.type == type }
                    ?.apply { positions.add(pos) }
                if(plotW != null && plotN != null) {
                    if(plotW != plotN) {
                        // two different adjacent plots with same type -> merge plot W into plot N
                        plotN.positions.addAll(plotW.positions)
                        plotW.positions.forEach { pos ->
                            plotsByPos[pos] = plotN
                        }
                        plotsByType[type]!!.remove(plotW)
                    } else {
                        plotsByPos[pos] = plotW
                    }
                } else if(plotW == null && plotN == null) {
                    val plotList = plotsByType.computeIfAbsent(type) { mutableListOf<Plot>() }
                    Plot(type, plotList.size, mutableSetOf(pos))
                        .also {
                            plotList.add(it)
                            plotsByPos[pos] = it
                        }
                } else {
                    plotsByPos[pos] = (plotW ?: plotN)!!
                }
            }
        }

        return plotsByType.values.flatMap { it }
    }

    fun part1(input: List<String>): Int = input.findPlots()
        .sumOf { plot -> plot.area * plot.perimeter }

    fun part2(input: List<String>): Int = input.findPlots()
        .sumOf { plot -> plot.area * plot.sides }
}
