package rendering

import ConsoleUI
import gameOfLife.GameOfLife
import gameOfLife.Point
import input.KeyHandler
import input.MouseHandler
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.glfw.GLFWErrorCallback
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11.*
import org.lwjgl.system.MemoryUtil.NULL

// Partially copied from java game engine tutorial https://www.youtube.com/watch?v=_UYxTtJQuuw&list=PLtrSb4XxIVbp8AKuEAlwNXDxr99e3woGE&index=2&ab_channel=GamesWithGabe
class Window(
    private val width: Int,
    private val height: Int,
    private val title: String,
    private val gameOfLife: GameOfLife
) {
    private var glfwWindow: Long = 0
    val keyHandler: KeyHandler = KeyHandler()
    val mouseHandler: MouseHandler = MouseHandler()
    private val camera: Camera = Camera()

    init {
        init()
        loop()
    }

    private fun init() {
        // Setup an error callback
        GLFWErrorCallback.createPrint(System.err).set()

        // Initialize GLFW
        check(glfwInit()) { "Unable to initialize GLFW." }

        // Configure GLFW
        glfwDefaultWindowHints()
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE)
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE)

        // Create the window
        glfwWindow = glfwCreateWindow(width, height, title, NULL, NULL)
        check(glfwWindow != NULL) { "Failed to create the GLFW window." }

        glfwSetCursorPosCallback(glfwWindow, mouseHandler::mousePosCallback)
        glfwSetMouseButtonCallback(glfwWindow, mouseHandler::mouseButtonCallback)
        glfwSetScrollCallback(glfwWindow, mouseHandler::mouseScrollCallback)
        glfwSetKeyCallback(glfwWindow, keyHandler::keyCallback)

        // Make the OpenGL context current
        glfwMakeContextCurrent(glfwWindow)
        // Enable v-sync
        glfwSwapInterval(1)

        // Make the window visible
        glfwShowWindow(glfwWindow)

        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities()
    }

    private fun loop() {
        var count = 0
        while (!glfwWindowShouldClose(glfwWindow)) {

            // Poll events
            glfwPollEvents()
            glClear(GL_COLOR_BUFFER_BIT)

            // Player Input
            camera.applyChanges(keyHandler, mouseHandler)
            gameOfLife.applyChanges(keyHandler, mouseHandler)

            // Render
            drawPointsAsSquares(gameOfLife.getAlivePoints(), camera)

            // Simulation speed
            if (count % (60/gameOfLife.getGenerationsPer60Frames()) == 0) {
                gameOfLife.nextGeneration()
                count = 0
            }
            count++

            mouseHandler.endFrame()
            glfwSwapBuffers(glfwWindow)
        }
        glfwDestroyWindow(glfwWindow)
        val consoleUI = ConsoleUI()
        consoleUI.mainMenu()
    }

    private fun drawPointsAsSquares(points: HashSet<Point>, camera: Camera) {
        val widthToHeightRatio = width.toDouble() / height.toDouble()

        val offsetX: Double = camera.position.x
        val offsetY: Double = camera.position.y * widthToHeightRatio
        val scale = camera.getScale()

        // Set the color for the squares
        glColor3f(1.0f, 1.0f, 1.0f) // White color

        // Size of each square
        val halfSquareWidth = 0.5 * scale
        val halfSquareHeight = halfSquareWidth * widthToHeightRatio

        for (point in points) {
            val x = (point.x.toDouble() - offsetX) * scale
            val y = (point.y.toDouble() - offsetY) * scale * widthToHeightRatio
            glBegin(GL_QUADS)
            glVertex2d(x - halfSquareWidth, y - halfSquareHeight) // Bottom-left
            glVertex2d(x + halfSquareWidth, y - halfSquareHeight) // Bottom-right
            glVertex2d(x + halfSquareWidth, y + halfSquareHeight) // Top-right
            glVertex2d(x - halfSquareWidth, y + halfSquareHeight) // Top-left
            glEnd()
        }
    }
}