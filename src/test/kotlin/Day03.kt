import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

object Day03Tests {
    @Test
    fun `Part 1 short`() {
        assertEquals(161, Day03.part1(listOf(
            "xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))"
        )))
    }

    @Test
    fun `Part 1 long`() {
        Day03.part1(readInput("Day03_long")).println()
    }

    @Test
    fun `Part 2 short`() {
        assertEquals(48, Day03.part2(listOf(
            "xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))"
        )))
    }

    @Test
    fun `Part 2 long`() {
        Day03.part2(readInput("Day03_long")).println()
    }
}
