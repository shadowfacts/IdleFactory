package net.shadowfacts.idlefactory.tile

import net.shadowfacts.idlefactory.util.Pos
import net.shadowfacts.idlefactory.util.Textures
import net.shadowfacts.idlefactory.world.World

/**
 * @author shadowfacts
 */
class TileFloor(world: World, pos: Pos) : Tile(TileFloorFactory, world, pos, Textures.FLOOR) {

	override val isReplaceable: Boolean
		get() = true

	override fun destroy() {

	}

}