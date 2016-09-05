package net.shadowfacts.idlefactory.player

import net.shadowfacts.idlefactory.item.Stack
import net.shadowfacts.idlefactory.inventory.SimpleInventory

/**
 * @author shadowfacts
 */
class PlayerInventory(val player: Player) : SimpleInventory(36) {

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