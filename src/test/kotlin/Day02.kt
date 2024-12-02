import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

object Day02Tests {
    @Test
    fun `Part 1 short`() {
        assertEquals(2, Day02.part1(readInput("Day02_short")))
    }

    @Test
    fun `Part 1 long`() {
        Day02.part1(readInput("Day02_long")).println()
    }

    @Test
    fun `Part 2 short`() {
        assertEquals(4, Day02.part2(readInput("Day02_short")))
    }

    @Test
    fun `Part 2 long`() {
        Day02.part2(readInput("Day02_long")).println()
    }
}
