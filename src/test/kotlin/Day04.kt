import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

object Day04Tests {
    val SHORT = """
        MMMSXXMASM
        MSAMXMSMSA
        AMXSXMAAMM
        MSAMASMSMX
        XMASAMXAMM
        XXAMMXXAMA
        SMSMSASXSS
        SAXAMASAAA
        MAMMMXMMMM
        MXMXAXMASX
    """.trimIndent().split('\n')

    @Test
    fun `Part 1 short`() {
        assertEquals(18, Day04.part1(SHORT))
    }

    @Test
    fun `Part 1 long`() {
        Day04.part1(readInput("Day04_long")).println()
    }

    @Test
    fun `Part 2 short`() {
        assertEquals(9, Day04.part2(SHORT))
    }

    @Test
    fun `Part 2 long`() {
        Day04.part2(readInput("Day04_long")).println()
    }
}
