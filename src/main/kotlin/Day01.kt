import kotlin.collections.component1
import kotlin.collections.component2
import kotlin.math.absoluteValue

object Day01 {
    fun List<String>.parseLists() = this
        .map { line -> line
            .split(Regex("\\s+"))
            .map { it.toInt() }
            .apply { assert(size == 2) }
        }
        .fold(ArrayList<Int>() to ArrayList<Int>()) { acc, (left, right) ->
            val (leftList, rightList) = acc
            leftList.add(left)
            rightList.add(right)
            acc
        }

    fun part1(input: List<String>): Int = input.parseLists()
        .also { (leftList, rightList) ->
            leftList.sort()
            rightList.sort()
        }
        .let { (leftList, rightList) ->
            leftList.mapIndexed { index, left ->
                val right = rightList[index]
                (left - right).absoluteValue
            }
        }
        .sum()

    fun part2(input: List<String>): Int = input.parseLists()
        .let { (leftList, rightList) ->
            leftList.sorted() to
            rightList.groupingBy { it }.eachCount()
        }
        .let { (leftList, rightMap) ->
            leftList.mapIndexed { index, left ->
                val rightCount = rightMap.getOrDefault(left, 0)
                left * rightCount
            }
        }
        .sum()
}
