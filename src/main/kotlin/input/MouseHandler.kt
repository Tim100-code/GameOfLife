package input

import org.lwjgl.glfw.GLFW.GLFW_PRESS
import org.lwjgl.glfw.GLFW.GLFW_RELEASE

// Partially copied from java game engine tutorial https://www.youtube.com/watch?v=88oZT7Aum6s&list=PLtrSb4XxIVbp8AKuEAlwNXDxr99e3woGE&index=3&ab_channel=GamesWithGabe

class MouseHandler {
    private var scrollY: Double = 0.0
    private var xPos: Double = 0.0
    private var yPos: Double = 0.0
    private var lastY: Double = 0.0
    private var lastX: Double = 0.0
    private val mouseButtonPressed = BooleanArray(3)
    private var isDragging = false

    fun mousePosCallback(window: Long, xpos: Double, ypos: Double) {
        lastX = xPos
        lastY = yPos
        xPos = xpos
        yPos = ypos
        isDragging = mouseButtonPressed[0] || mouseButtonPressed[1] || mouseButtonPressed[2]
    }

    fun mouseButtonCallback(window: Long, button: Int, action: Int, mods: Int) {
        if (action == GLFW_PRESS) {
            if (button < mouseButtonPressed.size) {
                mouseButtonPressed[button] = true
            }
        } else if (action == GLFW_RELEASE) {
            if (button < mouseButtonPressed.size) {
                mouseButtonPressed[button] = false
                isDragging = false
            }
        }
    }

    fun mouseScrollCallback(window: Long, xOffset: Double, yOffset: Double) {
        scrollY = yOffset
    }

    fun endFrame() {
        scrollY = 0.0
        lastX = xPos
        lastY = yPos
    }

    fun getScrollY(): Float = scrollY.toFloat()
}