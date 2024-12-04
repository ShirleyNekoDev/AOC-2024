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

    fun isSafe(report: List<Int>): Boolean {
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
        .count { report -> isSafe(report) }

    fun part2_isSafe(report: List<Int>): Boolean {
        if(isSafe(report))
            return true

        for(i in report.indices) {
            val fixedReport = report.toMutableList().apply { removeAt(i) }
            if(isSafe(fixedReport))
                return true
        }
        return false
    }

    fun part2(input: List<String>): Int = input.parseReports()
        .count { report -> part2_isSafe(report) }
}
