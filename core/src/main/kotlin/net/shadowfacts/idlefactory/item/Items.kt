package net.shadowfacts.idlefactory.item

import net.shadowfacts.idlefactory.util.Textures

/**
 * @author shadowfacts
 */
object ItemOreIron : ItemDefinition("ore_iron", Textures.IRON_ORE) {
	override fun register() {
		ItemRegistry.register(name, this, 0)
	}
}

object ItemIngotIron : ItemDefinition("ingot_iron", Textures.IRON_INGOT) {
	override fun register() {
		ItemRegistry.register(name, this, 1)
	}
}

fun initItems() {
	arrayOf(ItemOreIron, ItemIngotIron).forEach(ItemDefinition::register)
}