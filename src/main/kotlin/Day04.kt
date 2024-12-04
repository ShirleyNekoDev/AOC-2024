import utils.Direction

object Day04 {
    const val EMPTY_CHAR = '.'

    fun List<String>.charAt(x: Int, y: Int): Char? = getOrNull(y)?.getOrNull(x)

    fun List<String>.charAt(x: Int, y: Int, direction: Direction, distance: UInt): Char = charAt(
        x + direction.dx * distance.toInt(),
        y + direction.dy * distance.toInt()
    ) ?: EMPTY_CHAR

    fun List<String>.isMAXRayFromX(x: Int, y: Int, direction: Direction): Boolean =
        charAt(x, y, direction, 1u) == 'M' &&
        charAt(x, y, direction, 2u) == 'A' &&
        charAt(x, y, direction, 3u) == 'S'

    fun List<String>.countDirectionalMASFromX(x: Int, y: Int): Int = Direction.entries.sumOf { direction ->
        if(isMAXRayFromX(x, y, direction)) 1
        else @Suppress("USELESS_CAST") (0 as Int)
    }

    fun part1(input: List<String>): Int = input.mapIndexed { y, line ->
        line.mapIndexed { x, char ->
            if(char == 'X') input.countDirectionalMASFromX(x, y)
            else 0
        }.sum()
    }.sum()

    val MAS_PATTERN: Array<CharArray> = arrayOf(
        "MMSS".toCharArray(),
        "MSMS".toCharArray(),
        "SSMM".toCharArray(),
        "SMSM".toCharArray(),
    )

    fun List<String>.isMASX(x: Int, y: Int): Boolean = charArrayOf(
        charAt(x-1, y-1) ?: EMPTY_CHAR,
        charAt(x+1, y-1) ?: EMPTY_CHAR,
        charAt(x-1, y+1) ?: EMPTY_CHAR,
        charAt(x+1, y+1) ?: EMPTY_CHAR
    ).let { nwneswse ->
        MAS_PATTERN.any { pattern -> pattern.contentEquals(nwneswse) }
    }
    
    fun part2(input: List<String>): Int = input.mapIndexed { y, line ->
        line.mapIndexed { x, char ->
            if(char == 'A' && input.isMASX(x, y)) 1
            else 0
        }.sum()
    }.sum()
}
