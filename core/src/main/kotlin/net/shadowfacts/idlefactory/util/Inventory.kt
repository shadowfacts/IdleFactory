package net.shadowfacts.idlefactory.util

import net.shadowfacts.idlefactory.item.Stack
import net.shadowfacts.idlefactory.nbt.NBTSerializeable
import net.shadowfacts.idlefactory.nbt.impl.TagCompound
import net.shadowfacts.idlefactory.nbt.impl.TagList

/**
 * @author shadowfacts
 */
interface Inventory : Iterable<Stack?>, NBTSerializeable<TagList> {

	fun getSize(): Int

	operator fun get(slot: Int): Stack?

	operator fun set(slot: Int, stack: Stack?)

	fun insert(slot: Int, stack: Stack, simulate: Boolean): Stack? {
		if (slot < 0 || slot >= getSize()) throw IndexOutOfBoundsException("Invalid index $slot for inventory with size ${getSize()}")

		val current = get(slot)
		if (current == null) {
			if (!simulate) {
				set(slot, stack)
			}
			return null
		} else {
			if (current.type != stack.type) {
				return stack
			} else {
				val totalAmount = current.amount + stack.amount
				if (totalAmount <= 100) {
					if (!simulate) {
						set(slot, Stack(current.type, totalAmount))
					}
					return null
				} else {
					if (!simulate) {
						set(slot, Stack(current.type, 100))
					}
					return Stack(current.type, totalAmount - 100)
				}
			}
		}
	}

	fun extract(slot: Int, amount: Int, simulate: Boolean): Stack? {
		if (slot < 0 || slot >= getSize()) throw IndexOutOfBoundsException("Invalid index $slot for inventory with size ${getSize()}")

		val resource = get(slot)
		if (resource != null && amount > 0) {
			@Suppress("NAME_SHADOWING")
			val amount = Math.min(amount, resource.amount)

			if (!simulate) {
				set(slot, resource.decrementStackSize(amount))
			}

			return Stack(resource.type, amount)
		}
		return null
	}

	override fun serializeNBT(tag: TagList): TagList {
		for (i in 0.until(getSize())) {
			val stack = this[i]
			if (stack != null) {
				val compound = TagCompound("stack-$i")
				compound["slot"] = i
				compound["stack"] = stack.serializeNBT(TagCompound("stack"))
				tag.add(compound)
			}
		}
		return tag
	}

	override fun deserializeNBT(tag: TagList) {
		tag.forEach {
			if (it is TagCompound) {
				this[it.getInt("slot")] = Stack.deserializeNBT(it.getTag("stack")!!)
			}
		}
	}

}