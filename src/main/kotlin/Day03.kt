
object Day03 {
    val MUL_REGEX = Regex("mul\\((\\d{1,3}),(\\d{1,3})\\)")

    fun part1(input: List<String>): Long = input.asSequence()
        .flatMap { line -> MUL_REGEX.findAll(line) }
        .map { match ->
            val (left, right) = match.destructured
            left.toLong() * right.toLong()
        }
        .sum()

    val INSTRUCTION_REGEX = Regex("do\\(\\)|don't\\(\\)|mul\\((\\d{1,3}),(\\d{1,3})\\)")

    fun part2(input: List<String>): Long {
        var enabled  = true
        return input.asSequence()
            .flatMap { line -> INSTRUCTION_REGEX.findAll(line) }
            .map { match ->
                when(match.value[2]) {
                    '(' -> { // do()
                        enabled = true
                    }
                    'n' -> { // don't()
                        enabled = false
                    }
                    'l' -> { // mul(l,r)
                        val (left, right) = match.destructured
                        return@map if(enabled)
                            left.toLong() * right.toLong()
                        else 0
                    }
                }
                0 // non mul instruction (nothing to add)
            }
            .sum()
    }
}
