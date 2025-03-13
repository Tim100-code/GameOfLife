package rendering

import input.KeyHandler
import input.MouseHandler
import input.UserControllable
import org.joml.Vector2d
import org.joml.Vector2f
import org.lwjgl.glfw.GLFW

class Camera : UserControllable {
    var position: Vector2d = Vector2d(0.0,0.0)

    private var scale: Float = 0.01f
    private var movementSpeed: Float = 0.01f
    private var direction: Vector2f = Vector2f(0f,0f)


    fun getScale(): Float = scale

    override fun applyChanges(keyHandler: KeyHandler, mouseHandler: MouseHandler) {
        direction = Vector2f(0f,0f)
        if (keyHandler.isKeyPressed(GLFW.GLFW_KEY_W)) direction.y = 1f
        if (keyHandler.isKeyPressed(GLFW.GLFW_KEY_A)) direction.x = -1f
        if (keyHandler.isKeyPressed(GLFW.GLFW_KEY_S)) direction.y = -1f
        if (keyHandler.isKeyPressed(GLFW.GLFW_KEY_D)) direction.x = 1f
        position.add(direction.mul(movementSpeed/scale))

        if (mouseHandler.getScrollY() < 0.0f) {
            scale *= 0.9f
        } else if (mouseHandler.getScrollY() > 0.0f) {
            scale *= 1.1f
        }
    }
}