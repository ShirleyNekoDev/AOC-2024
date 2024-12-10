import org.junit.jupiter.api.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.text.single

object Day09Tests {
    val SHORT = "2333133121414131402"

    val SHORT_UNPACKED =             "00...111...2...333.44.5555.6666.777.888899"
    val SHORT_COMPACTED =            "0099811188827773336446555566"
    val SHORT_COMPACT_DEFRAGMENTED = "00992111777.44.333....5555.6666.....8888"

    fun parseFS(fsString: String): Day09.FileSystem {
        return Day09.FileSystem(fsString.map { char ->
            when(char) {
                '.' -> -1
                else -> char.digitToInt()
            }
        }.toMutableList())
    }

    fun Day09.FileSystem.printFS() = apply {
        println(toString())
    }

    @Test
    fun `test unpackInput()`() {
        val expected = parseFS(SHORT_UNPACKED).printFS()
        assertContentEquals(
            expected.blocks,
            Day09.FileSystem
                .parse(SHORT)
                .printFS()
                .blocks
        )
    }

    @Test
    fun `test compactFS()`() {
        val expected = parseFS(SHORT_COMPACTED).printFS()
        assertContentEquals(
            expected.blocks,
            parseFS(SHORT_UNPACKED)
                .apply { compact() }
                .printFS()
                .blocks
        )
    }

    @Test
    fun `Part 1 short`() {
        assertEquals(1928, Day09.part1(SHORT))
    }

    @Test
    fun `Part 1 long`() {
        Day09.part1(readInput("Day09_long").single()).println()
    }

    @Test
    fun `Part 2 short`() {
        assertEquals(2858, Day09.part2(SHORT))
        assertEquals(385, Day09.part2("1010101010101010101010"))
        assertEquals(1325, Day09.part2("354631466260"))
        assertEquals(16, Day09.part2("14113"))
        assertEquals(132, Day09.part2("12345"))
    }

    @Test
    fun `Part 2 long`() {
        Day09.part2(readInput("Day09_long").single()).println()
    }
}
