import kotlin.test.Test
import kotlin.test.assertEquals

object Day01Tests {
    val SHORT = """
        3   4
        4   3
        2   5
        1   3
        3   9
        3   3
    """.trimIndent().split('\n')

    @Test
    fun `Part 1 short`() {
        assertEquals(11, Day01.part1(SHORT))
    }

    @Test
    fun `Part 1 long`() {
        Day01.part1(readInput("Day01_long")).println()
    }

    @Test
    fun `Part 2 short`() {
        assertEquals(31, Day01.part2(SHORT))
    }

    @Test
    fun `Part 2 long`() {
        Day01.part2(readInput("Day01_long")).println()
    }
}
