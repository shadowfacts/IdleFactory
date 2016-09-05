package net.shadowfacts.idlefactory.inventory

import net.shadowfacts.idlefactory.item.Stack

/**
 * @author shadowfacts
 */
open class SimpleInventory(size: Int) : Inventory {

	private val inventory: Array<Stack?> = arrayOfNulls(size)

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

	override fun iterator(): Iterator<Stack?> {
		return inventory.iterator()
	}

}