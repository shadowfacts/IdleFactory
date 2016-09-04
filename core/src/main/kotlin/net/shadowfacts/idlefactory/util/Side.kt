package net.shadowfacts.idlefactory.util

/**
 * @author shadowfacts
 */
enum class Side(val xOffset: Int, val yOffset: Int) {

	UP(0, 1),
	DOWN(0, -1),
	RIGHT(1, 0),
	LEFT(-1, 0)

}