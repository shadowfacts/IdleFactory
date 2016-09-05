package net.shadowfacts.idlefactory.player.tool

import net.shadowfacts.idlefactory.nbt.NBTSerializable
import net.shadowfacts.idlefactory.nbt.impl.TagCompound
import net.shadowfacts.idlefactory.player.Player

/**
 * @author shadowfacts
 */
class ToolManager(val player: Player) : NBTSerializable<TagCompound> {

	private var tools: Array<Tool> = arrayOf(ToolSelector, ToolPlace, ToolRemove)

	var selectedTool: Int = 0
		private set
	fun onNumberKeyPressed(keyCode: Int): Boolean {
		val num = keyCode - 8
		if (num >= 0 && num < tools.size) {
			selectedTool = num
			return true
		}
		return false
	}

	fun getSelectedTool(): Tool {
		return tools[selectedTool]
	}

	fun getToolCount(): Int {
		return tools.size
	}

	operator fun get(index: Int): Tool {
		return tools[index]
	}

	override fun serializeNBT(tag: TagCompound): TagCompound {
		tag["selectedTool"] = selectedTool
		tools.forEach {
			it.serializeNBT(tag)
		}
		return tag
	}

	override fun deserializeNBT(tag: TagCompound) {
		selectedTool = tag.getInt("selectedTool")
		tools.forEach {
			it.deserializeNBT(tag)
		}
	}

}
