package net.shadowfacts.idlefactory.item

import net.shadowfacts.idlefactory.nbt.NBTSerializeable
import net.shadowfacts.idlefactory.nbt.impl.TagCompound

/**
 * @author shadowfacts
 */
data class Stack(val type: ItemDefinition, val amount: Int) : NBTSerializeable<TagCompound> {

	fun incrementStackSize(amount: Int): Stack {
		return Stack(type, this.amount + amount)
	}

	fun decrementStackSize(amount: Int): Stack? {
		val newAmount = this.amount - amount
		if (newAmount <= 0) {
			return null
		} else {
			return Stack(type, newAmount)
		}
	}

	override fun serializeNBT(tag: TagCompound): TagCompound {
		tag["type"] = ItemRegistry.getId(type)
		tag["amount"] = amount
		return tag
	}

	override fun deserializeNBT(tag: TagCompound) {
		// NO-OP use Stack.Companion.deserializeNBT
	}

	companion object {

		fun deserializeNBT(tag: TagCompound): Stack? {
			val id = tag.getShort("type")
			return if (ItemRegistry.exists(id)) Stack(ItemRegistry[id]!!, tag.getInt("amount")) else null
		}

	}

}