import kotlin.text.split

typealias Page = Int
typealias UpdatePages = List<Page>
typealias PageOrderRules = Map<Page, List<Page>>

object Day05 {
    data class Input(
        val pageOrderRules: PageOrderRules,
        val updates: List<UpdatePages>,
    )

    fun List<String>.parseInput(): Input {
        val pageOrderRules = takeWhile { line -> line.isNotBlank() }
        return Input(
            pageOrderRules = pageOrderRules
                .map { line -> line
                    .split('|')
                    .map { it.toInt() }
                    .apply { assert(size == 2) }
                }
                .groupBy({ (left, _) -> left }) { (_, right) -> right }
            ,
            updates = subList(pageOrderRules.size + 1, size)
                .map { line -> line
                    .split(',')
                    .map { it.toInt() }
                    .apply { assert(isNotEmpty()) }
                }
        )
    }

    fun isCorrectlyOrdered(updatePages: UpdatePages, pageOrderRules: PageOrderRules): Boolean {
        updatePages
            .forEachIndexed { index, page ->
                val orderRules = pageOrderRules[page]
                if(orderRules != null) {
                    val noPageViolatesRules = updatePages
                        .subList(0, index)
                        .none { prePage ->
                            // check that no [prePage] in front of [page] is in the ruleset for [page]
                            // as this would violate that that page must be after [page]
                            prePage in orderRules
                        }
                    if(!noPageViolatesRules)
                        return false
                }
            }
        return true
    }

    fun part1(input: List<String>): Int = input.parseInput()
        .run {
            updates
                .filter { update -> isCorrectlyOrdered(update, pageOrderRules) }
                .sumOf { update ->
                    update[update.size / 2]
                }
        }

    fun <T> MutableList<T>.moveInFrontOf(index: Int, position: Int) {
        assert(index > position)
        val element = this.removeAt(index)
        this.add(position, element)
    }

    fun orderUpdate(updatePages: UpdatePages, pageOrderRules: PageOrderRules): UpdatePages {
        val sortedPages = updatePages.toMutableList()
        for(i in sortedPages.indices) {
            val page = sortedPages[i]
            val orderRules = pageOrderRules[page]
            if(orderRules != null) {
                for(j in 0 until i) {
                    val prePage = sortedPages[j]
                    if(prePage in orderRules) {
                        sortedPages.moveInFrontOf(i, j)
                        break
                    }
                }
            }
        }
        assert(isCorrectlyOrdered(sortedPages, pageOrderRules))
        return sortedPages
    }

    fun part2(input: List<String>): Int = input.parseInput()
        .run {
            updates
                .filter { update -> !isCorrectlyOrdered(update, pageOrderRules) }
                .map { update -> orderUpdate(update, pageOrderRules) }
                .sumOf { update ->
                    update[update.size / 2]
                }
        }
}
