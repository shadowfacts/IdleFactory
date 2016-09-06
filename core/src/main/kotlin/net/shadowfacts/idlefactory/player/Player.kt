package net.shadowfacts.idlefactory.player

import net.shadowfacts.idlefactory.player.tool.ToolManager
import net.shadowfacts.idlefactory.item.ItemIngotIron
import net.shadowfacts.idlefactory.item.ItemTile
import net.shadowfacts.idlefactory.item.ItemOreIron
import net.shadowfacts.idlefactory.item.Stack
import net.shadowfacts.idlefactory.nbt.NBTSerializable
import net.shadowfacts.idlefactory.nbt.impl.TagCompound
import net.shadowfacts.idlefactory.nbt.impl.TagList
import net.shadowfacts.idlefactory.tile.*
import net.shadowfacts.idlefactory.tile.factory.ItemTileTest
import net.shadowfacts.idlefactory.tile.factory.ItemTileWall
import net.shadowfacts.idlefactory.util.Textures

/**
 * @author shadowfacts
 */
class Player : NBTSerializable<TagCompound> {

	val inventory = PlayerInventory(this)
	val tools = ToolManager(this)

	var money = 20000
		private set

	constructor() {
		inventory[0] = Stack(ItemOreIron, 9)
		inventory[1] = Stack(ItemTileTest, 1)
		inventory[9] = Stack(ItemIngotIron, 11)
		inventory[18] = Stack(ItemTileWall, 1)
	}

	override fun serializeNBT(tag: TagCompound): TagCompound {
		tag["inventory"] = inventory.serializeNBT(TagList("inventory"))
		tools.serializeNBT(tag)
		tag["money"] = money
		return tag
	}

	override fun deserializeNBT(tag: TagCompound) {
		inventory.deserializeNBT(tag.getTag<TagList>("inventory")!!)
		tools.deserializeNBT(tag)
		money = tag.getInt("money")
	}

	fun addMoney(amount: Int) {
		money += amount
	}

	fun removeMoney(amount: Int): Boolean {
		if (money >= amount) {
			money -= amount
			return true
		}
		return false
	}

}