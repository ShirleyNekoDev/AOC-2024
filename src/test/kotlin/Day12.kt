import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

object Day12Tests {
    val SHORT1 = """
        AAAA
        BBCD
        BBCC
        EEEC
    """.trimIndent().split('\n')

    val SHORT2 = """
        OOOOO
        OXOXO
        OOOOO
        OXOXO
        OOOOO
    """.trimIndent().split('\n')

    val SHORT3 = """
        RRRRIICCFF
        RRRRIICCCF
        VVRRRCCFFF
        VVRCCCJFFF
        VVVVCJJCFE
        VVIVCCJJEE
        VVIIICJJEE
        MIIIIIJJEE
        MIIISIJEEE
        MMMISSJEEE
    """.trimIndent().split('\n')

    @Test
    fun `Part 1 short`() {
        assertEquals(140, Day12.part1(SHORT1))
        assertEquals(772, Day12.part1(SHORT2))
        assertEquals(1930, Day12.part1(SHORT3))
    }

    @Test
    fun `Part 1 long`() {
        Day12.part1(readInput("Day12_long")).println()
    }

    val SHORT4 = """
        EEEEE
        EXXXX
        EEEEE
        EXXXX
        EEEEE
    """.trimIndent().split('\n')

    val SHORT5 = """
        AAAAAA
        AAABBA
        AAABBA
        ABBAAA
        ABBAAA
        AAAAAA
    """.trimIndent().split('\n')

    @Test
    fun `Part 2 short`() {
        assertEquals(80, Day12.part2(SHORT1))
        assertEquals(436, Day12.part2(SHORT2))
        assertEquals(1206, Day12.part2(SHORT3))
        assertEquals(236, Day12.part2(SHORT4))
        assertEquals(368, Day12.part2(SHORT5))
    }

    @Test
    fun `Part 2 long`() {
        Day12.part2(readInput("Day12_long")).println()
    }
}
