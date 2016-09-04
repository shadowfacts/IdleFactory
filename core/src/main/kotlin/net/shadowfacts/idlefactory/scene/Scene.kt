package net.shadowfacts.idlefactory.scene

/**
 * @author shadowfacts
 */
abstract class Scene {

	abstract fun create()

	open fun open() {}

	abstract fun render(delta: Float)

	open fun resize(width: Int, height: Int) {}

	abstract fun dispose()

	open fun close() {}

}