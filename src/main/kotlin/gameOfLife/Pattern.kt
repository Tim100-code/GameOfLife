package gameOfLife

import java.io.File

class Pattern(filePath: String = "") {
    private val file = File(filePath)

    fun read() : HashSet<Point> {
        val points = HashSet<Point>()
        var text = file.readText(Charsets.UTF_8)
        if (!text.contains("B3/S23")) throw Exception("Unfamiliar rule")
        text = text.lineSequence().drop(2).joinToString("\n") //Skip the first 2 lines

        var x = 0
        var y = 0
        var count = 0
        for (char in text.toCharArray()) {
            when {
                char.isDigit() -> {
                    count = count * 10 + (char - '0')
                }
                char == 'o' -> {
                    if (count == 0) {
                        points.add(Point(x, y))
                        x++
                    } else {
                        for (i in 0..<count) {
                            points.add(Point(x, y))
                            x++
                        }
                    }
                    count = 0
                }
                char == 'b' -> {
                    if (count == 0) {
                        x++
                    } else {
                        x += count
                    }
                    count = 0
                }
                char == '$' -> {
                    x = 0
                    if (count == 0) {
                        y--
                    } else {
                        y -= count
                    }
                    count = 0
                }
            }
        }
        return points
    }

    override fun toString(): String {
        return file.name
    }
}