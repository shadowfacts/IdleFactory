package net.shadowfacts.idlefactory.tile.factory

import com.badlogic.gdx.graphics.Texture
import net.shadowfacts.idlefactory.item.ItemDefinition
import net.shadowfacts.idlefactory.item.ItemRegistry
import net.shadowfacts.idlefactory.tile.Tile
import net.shadowfacts.idlefactory.tile.TileRegistry
import net.shadowfacts.idlefactory.util.Pos
import net.shadowfacts.idlefactory.world.World

/**
 * @author shadowfacts
 */
abstract class TileFactory(val name: String, val texture: Texture, val clazz: Class<out Tile>) {

	abstract fun register()

	fun createTile(world: World, pos: Pos): Tile {
		val tile = clazz.getConstructor(World::class.java, Pos::class.java).newInstance(world, pos)
		tile.initComponents()
		return tile
	}

	fun getItemDefinition(): ItemDefinition? {
		val id = TileRegistry.getId(this)
		val itemId = (Short.MAX_VALUE - id).toShort()
		if (ItemRegistry.exists(itemId)) {
			return ItemRegistry[itemId]
		} else {
			return null
		}
	}

}