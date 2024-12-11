import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

object Day06Tests {
    val SHORT = """
        ....#.....
        .........#
        ..........
        ..#.......
        .......#..
        ..........
        .#..^.....
        ........#.
        #.........
        ......#...
    """.trimIndent().split('\n')

    @Test
    fun `Part 1 short`() {
        assertEquals(41, Day06.part1(SHORT))
    }

    @Test
    fun `Part 1 long`() {
        Day06.part1(readInput("Day06_long")).println()
    }

    @Test
    fun `Part 2 short`() {
        assertEquals(9, Day06.part2(SHORT))
    }

    @Test
    fun `Part 2 long`() {
        Day06.part2(readInput("Day06_long")).println()
    }
}
