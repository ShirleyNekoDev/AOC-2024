import java.util.LinkedList
import kotlin.text.forEachIndexed

typealias Index = Int
typealias Size = Int

object Day09 {

    @JvmInline
    value class FileSystem(val blocks: MutableList<Int>) {
        companion object {
            fun parse(input: String): FileSystem {
                return FileSystem(buildList<Int> {
                    var fileIndex = 0
                    input.forEachIndexed { i, char ->
                        // use -1 for free space
                        val fsEntry = if (i % 2 == 0) fileIndex++ else -1
                        val fileBlockCount = char.digitToInt()
                        // expand entry to its size
                        (0 until fileBlockCount).forEach { add(fsEntry) }
                    }
                }.toMutableList())
            }
        }

        private fun nextFreeBlockIndexIn(startIndexIncl: Int): Int {
            for (i in startIndexIncl until blocks.size) {
                if(blocks[i] == -1)
                    return i
            }
            return -1
        }

        fun compact() {
            var frontIndex = 0
            while(true) {
                frontIndex = nextFreeBlockIndexIn(frontIndex)
                if(frontIndex == -1)
                    break
                blocks[frontIndex] = blocks.removeLast()
            }
        }

        data class File(
            val id: Int,
            val startIndex: Index,
            val size: Size,
        ) {
            fun selectViewOn(blocks: MutableList<Int>, length: Size = size): MutableList<Int> =
                blocks.subList(startIndex, startIndex+length)

            override fun toString(): String = buildString {
                if(id != -1)
                    append("[$id]")
                append("$startIndex..${startIndex+size-1}($size)")
            }
        }

        fun fragmentlessCompact() {
            // ~~~~~ build meta data structures
            val spaces = LinkedList<File>()
            val reverseFiles: List<File> = buildList {
                var currentFile = blocks.last()
                var size = 0
                for(index in blocks.indices.reversed()) {
                    val block = blocks[index]
                    if(block != currentFile) {
                        val blockIndex = index+1
                        if(currentFile == -1) {
                            spaces.addFirst(File(-1, blockIndex, size))
                        } else {
                            add(File(currentFile, blockIndex, size))
                        }
                        currentFile = block
                        size = 0
                    }
                    size++
                }
                add(File(currentFile, 0, size))
            }

            // ~~~~~ perform compacting
            fileLoop@ for(file in reverseFiles) {
                var freeSpace: File? = null
                val freeSpaceIter = spaces.listIterator()
                freeSpaceLoop@ while(freeSpaceIter.hasNext()) {
                    val space = freeSpaceIter.next()
                    if(space.startIndex+space.size > file.startIndex)
                        continue@fileLoop
                    if(space.size >= file.size) {
                        freeSpace = space
                        break@freeSpaceLoop
                    }
                }
                if(freeSpace == null)
                    continue@fileLoop

                // remove file from its position (replace with free space)
                file.selectViewOn(blocks).fill(-1)
                // replace the free space with the file
                freeSpace.selectViewOn(blocks, length = file.size).fill(file.id)

                if(freeSpace.size == file.size) {
                    // drop space from meta list
                    freeSpaceIter.remove()
                } else {
                    val oldFreeSpace = freeSpace
                    // shrink to remaining free space
                    freeSpace = freeSpace.copy(
                        startIndex = freeSpace.startIndex + file.size,
                        size = freeSpace.size - file.size
                    )
                    freeSpaceIter.set(freeSpace)
                }
                spaces.descendingIterator().apply {
                    while(hasNext()) {
                        val space = next()
                        if(space.startIndex > file.startIndex)
                            remove()
                        else break
                    }
                }
            }
        }

        fun checksum(): Long = blocks.foldIndexed(0) { index, acc, fileId ->
            if(fileId == -1) acc
            else acc + (index * fileId.toLong())
        }

        override fun toString(): String = buildString {
            var b = true
            var f = -1
            blocks.forEach { fileId -> when {
                fileId < 0 -> append('.')
                else -> {
//                    append(fileId)
                    append(if(b) 'O' else 'X')
                    if(f != fileId) {
                        b = !b
                        f = fileId
                    }
                }
            } }
        }.trimEnd('.')
    }

    fun part1(input: String): Long = FileSystem.parse(input)
        .apply { compact() }
        .checksum().toLong()

    fun part2(input: String): Long = FileSystem.parse(input)
        .apply { fragmentlessCompact() }
        .checksum().toLong()
}
