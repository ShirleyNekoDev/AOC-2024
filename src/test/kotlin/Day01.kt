import kotlin.test.Test
import kotlin.test.assertEquals

object Day01Tests {
    @Test
    fun `Part 1 short`() {
        assertEquals(11, Day01.part1(readInput("Day01_short")))
    }

    @Test
    fun `Part 1 long`() {
        Day01.part1(readInput("Day01_long")).println()
    }

    @Test
    fun `Part 2 short`() {
        assertEquals(31, Day01.part2(readInput("Day01_short")))
    }

    @Test
    fun `Part 2 long`() {
        Day01.part2(readInput("Day01_long")).println()
    }
}
