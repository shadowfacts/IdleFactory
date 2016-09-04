package net.shadowfacts.idlefactory.item

import net.shadowfacts.idlefactory.tile.TileFactory
import net.shadowfacts.idlefactory.tile.TileRegistry

/**
 * @author shadowfacts
 */
abstract class ItemTile(val tile: TileFactory) : ItemDefinition(tile.name, tile.texture) {

	override fun register() {
		ItemRegistry.register(name, this, (Short.MAX_VALUE - TileRegistry.getId(tile)).toShort())
	}

}