package input

interface UserControllable {
    fun applyChanges(keyHandler: KeyHandler, mouseHandler: MouseHandler)
}