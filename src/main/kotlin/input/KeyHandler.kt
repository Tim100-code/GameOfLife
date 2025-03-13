package input

import org.lwjgl.glfw.GLFW.GLFW_PRESS
import org.lwjgl.glfw.GLFW.GLFW_RELEASE

// Partially copied from java game engine tutorial https://www.youtube.com/watch?v=88oZT7Aum6s&list=PLtrSb4XxIVbp8AKuEAlwNXDxr99e3woGE&index=3&ab_channel=GamesWithGabe
class KeyHandler {
    private val keyPressed: Array<Boolean> = Array(350) { false }

    fun keyCallback(window: Long, key: Int, scancode: Int, action: Int, mods: Int) {
        if (action == GLFW_PRESS) {
            keyPressed[key] = true
        } else if (action == GLFW_RELEASE) {
            keyPressed[key] = false
        }
    }

    fun isKeyPressed(keyCode: Int) : Boolean {
        return keyPressed[keyCode]
    }

    fun isKeyJustPressed(keyCode: Int) : Boolean {
        if (keyPressed[keyCode]) {
            keyPressed[keyCode] = false
            return true
        }
        return false
    }
}