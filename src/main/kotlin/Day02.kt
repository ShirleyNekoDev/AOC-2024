import kotlin.collections.component1
import kotlin.math.absoluteValue

object Day02 {
    val ACCEPTED_RANGE = 1 .. 3
    fun isInAcceptedRange(difference: Int) = difference.absoluteValue in ACCEPTED_RANGE

    fun List<String>.parseReports() = this
        .map { line -> line
            .split(Regex("\\s+"))
            .map { it.toInt() }
        }

    fun isMonotone(difference: Int, increasing: Boolean): Boolean =
        if(increasing) difference > 0 else difference < 0

    fun part1_isSafe(report: List<Int>): Boolean {
        return report
            .windowed(2)
            .map { (first, second) -> second - first }
            .run {
                if(first() > 0) {
                    // increasing
                    all { difference ->
                        isInAcceptedRange(difference) &&
                        isMonotone(difference, true)
                    }
                } else {
                    // decreasing
                    all { difference ->
                        isInAcceptedRange(difference) &&
                        isMonotone(difference, false)
                    }
                }
            }
    }

    fun part1(input: List<String>): Int = input.parseReports()
        .count { report -> part1_isSafe(report) }

    /**
     * @return
     * * -1 does not require fixing
     * * -2 can not be fixed
     * * otherwise the index for the first level to remove to maybe fix this report
     */
    fun findFirstInvalidLevel(report: List<Int>): Int {
        var isIncreasing = true
        report.windowed(2)
            .map { (first, second) -> second - first }
            .forEachIndexed { index, difference ->
                if(!isInAcceptedRange(difference)) {
                    // use problem dampener on next level
//                    println("> fix range at ${index + 1}")
                    return index + 1
                }

                if(index == 0) {
                    // assume first pair will tell us the direction
                    isIncreasing = difference > 0
//                    println("> isIncreasing=$isIncreasing")
                } else {
//                    println("> index=$index difference=$difference")
                    if(!isMonotone(difference, isIncreasing)) {
                        if(index == 1) {
                            // could be first or second level which is faulty
                            // difference = report[2] - report[1]
                            // we can not be sure which direction is correct here, lets assume that the pair after this knows
                            isIncreasing = (report[3] - report[2]) > 0
                            val diff1Dropped = report[2] - report[0]
                            if(isMonotone(diff1Dropped, isIncreasing) && isInAcceptedRange(diff1Dropped)) {
                                // looks good if second level is dropped
//                                println("> fix range at 1")
                                return 1
                            } else if (isMonotone(difference, isIncreasing)) {
                                // drop first level
//                                println("> fix range at 0")
                                return 0
                            } else {
                                // this report can not be fixed because we still would have problems with monotony
                                return -2
                            }
                        } else {
                            val previous = report[index - 1]
                            val next = report[index + 1]
//                            println("> $previous, ${report[index]}, $next")
                            // next level is faulty
//                            println("> fix range at ${ index + 1}")
                            return index + 1
                        }
                    }
                }
            }
        return -1
    }

    fun part2_isSafe(report: List<Int>): Boolean {
        return when(val useDamperAtIndex = findFirstInvalidLevel(report)) {
            -1 -> {
                assert(part1_isSafe(report))
                true
            }
            -2 -> false
            else -> {
//                println("use dampener at index=$useDamperAtIndex (value=${report[useDamperAtIndex]}) on $report")
                val fixedReport = report.toMutableList().apply { removeAt(useDamperAtIndex) }
                val isFixed = part1_isSafe(fixedReport)
                if(!isFixed)
                    println("report $report could not be fixed by removing index=$useDamperAtIndex (value=${report[useDamperAtIndex]})")
//                println("has ${if(isFixed) "" else "not "}fixed the problem resulting in $fixedReport")
                isFixed
            }
        }
    }

    fun part2(input: List<String>): Int = input.parseReports()
        .count { report -> part2_isSafe(report) }
}
