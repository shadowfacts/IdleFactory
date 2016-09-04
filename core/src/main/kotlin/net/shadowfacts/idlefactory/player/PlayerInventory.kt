package net.shadowfacts.idlefactory.player

import net.shadowfacts.idlefactory.item.Stack
import net.shadowfacts.idlefactory.util.Inventory

/**
 * @author shadowfacts
 */
class PlayerInventory(val player: Player) : Inventory {

	private val inventory: Array<Stack?> = kotlin.arrayOfNulls(36)

	override fun getSize(): Int {
		return inventory.size
	}

	override operator fun get(slot: Int): Stack? {
		if (slot < 0 || slot >= getSize()) throw IndexOutOfBoundsException("Invalid index $slot for inventory with size ${getSize()}")

		return inventory[slot]
	}

	override operator fun set(slot: Int, stack: Stack?) {
		if (slot < 0 || slot >= getSize()) throw IndexOutOfBoundsException("Invalid index $slot for inventory with size ${getSize()}")

		inventory[slot] = stack
	}

	override operator fun iterator(): Iterator<Stack?> {
		return inventory.iterator()
	}

	fun insert(stack: Stack) {
		@Suppress("NAME_SHADOWING")
		var stack: Stack? = stack
		for (i in 0.until(getSize())) {
			if (stack == null) {
				return
			}
			stack = insert(i, stack, false)
		}
	}

}