package net.shadowfacts.idlefactory.util

import com.badlogic.gdx.math.Vector3

/**
 * @author shadowfacts
 */
data class Pos(val x: Int, val y: Int) {

	val renderX by lazy {
		x * 21f
	}

	val renderY by lazy {
		y * 21f
	}

	fun offset(side: Side, amount: Int): Pos {
		return Pos(x + (side.xOffset * amount), y + (side.yOffset * amount))
	}

	fun offset(side: Side): Pos = offset(side, 1)

	fun up(amount: Int): Pos = offset(Side.UP, amount)

	fun up(): Pos = up(1)

	fun down(amount: Int): Pos = offset(Side.DOWN, amount)

	fun down(): Pos = down(1)

	fun left(amount: Int): Pos = offset(Side.RIGHT, amount)

	fun left(): Pos = left(1)

	fun right(amount: Int): Pos = offset(Side.LEFT, amount)

	fun right(): Pos = right(1)

	fun toLong(): Long {
		return (x.toLong() shl 32) or y.toLong()
	}

	override fun toString(): String {
		return "(x=$x, $y)"
	}

	companion object {

		val ORIGIN = Pos(0, 0)

		fun fromLong(value: Long): Pos {
			val x = (value shr 32).toInt()
			val y = (value and 0xffffffff).toInt()
			return Pos(x, y)
		}

	}

	fun toVec3(): Vector3 {
		return Vector3(x.toFloat(), y.toFloat(), 0f)
	}

}