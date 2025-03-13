import gameOfLife.GameOfLife
import gameOfLife.Pattern
import rendering.Window
import java.io.File
import java.util.*

class ConsoleUI {
    fun mainMenu() {
        println(title())
        while (true) {
            println("\nChoose:\n" +
                    "1) Help\n" +
                    "2) Choose Pattern\n" +
                    "3) Exit")
            val choice = nextIntWithinRange(1,3)
            when (choice) {
                1 -> help()
                2 -> {
                    Window(1600, 900, "Game Of Life", GameOfLife(selectPattern()))
                    break
                }
                3 -> break
            }
        }
    }

    private fun title() : String {
        return "   _____                         ____   __   _      _  __     \n" +
                "  / ____|                       / __ \\ / _| | |    (_)/ _|    \n" +
                " | |  __  __ _ _ __ ___   ___  | |  | | |_  | |     _| |_ ___ \n" +
                " | | |_ |/ _` | '_ ` _ \\ / _ \\ | |  | |  _| | |    | |  _/ _ \\\n" +
                " | |__| | (_| | | | | | |  __/ | |__| | |   | |____| | ||  __/\n" +
                "  \\_____|\\__,_|_| |_| |_|\\___|  \\____/|_|   |______|_|_| \\___|"
    }

    private fun help() {
        println("Choose help: \n" +
                "1) How to use\n" +
                "2) How to add a Pattern\n" +
                "3) Back")
        val choice = nextIntWithinRange(1,3)
        when (choice) {
            1 -> println(howToUse())
            2 -> println(howToAddPattern())
        }
    }

    private fun howToUse() : String {
        return "How to use:\n" +
                "1) Choose a pattern in console\n" +
                "2) Press space to pause/unpause\n" +
                "3) Adjust simulation speed with Up and Down arrow keys\n" +
                "4) Move with W/A/S/D\n" +
                "5) Zoom in and out using scroll wheel (Sometimes you need to zoom out in order to see the pattern)"
    }

    private fun howToAddPattern() : String {
        return "How to add a Pattern:\n" +
                "1) Go to Golly website (https://golly.sourceforge.io/webapp/golly.html)\n" +
                "2) Choose the pattern (Or make your own) with the default rule B3/S23\n" +
                "3) Download it as .rle file\n" +
                "4) Add it to src/main/resources/patterns directory\n" +
                "5) Profit"
    }

    private fun selectPattern() : Pattern {
        println("Select pattern: ")
        val patterns = getPatternsFromFolder("src/main/resources/patterns")
        for (i in 1..patterns.size) {
            val pattern = patterns[i-1]
            println("$i) $pattern")
        }

        val choice = nextIntWithinRange(1, patterns.size)
        return patterns[choice-1]
    }

    private fun getPatternsFromFolder(folderPath: String): Array<Pattern> {
        val files = File(folderPath).listFiles()
        val patterns = Array(files!!.size) { Pattern() }
        for (i in files.indices) {
            val pattern = Pattern(files[i].path)
            patterns[i] = pattern
        }
        return patterns
    }

    private fun nextIntWithinRange(min: Int, max: Int): Int {
        var choice: Int
        while (true) {
            val scanner = Scanner(System.`in`)
            try {
                choice = scanner.nextInt()
            } catch (_: Exception) {continue}
            if (choice in min..max) break
        }
        return choice
    }
}