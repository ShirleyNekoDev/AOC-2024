import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

object Day02Tests {
    val SHORT = """
        7 6 4 2 1
        1 2 7 8 9
        9 7 6 2 1
        1 3 2 4 5
        8 6 4 4 1
        1 3 6 7 9
    """.trimIndent().split('\n')

    @Test
    fun `Part 1 short`() {
        assertEquals(2, Day02.part1(SHORT))
    }

    @Test
    fun `Part 1 long`() {
        Day02.part1(readInput("Day02_long")).println()
    }

    @Test
    fun `Part 2 short`() {
        assertEquals(4, Day02.part2(SHORT))
    }

    @Test
    fun `Part 2 long`() {
        Day02.part2(readInput("Day02_long")).println()
    }
}
