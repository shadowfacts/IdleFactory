package net.shadowfacts.idlefactory.tile.impl

import net.shadowfacts.idlefactory.tile.TileSingleTexture
import net.shadowfacts.idlefactory.tile.factory.TileFloorFactory
import net.shadowfacts.idlefactory.util.Pos
import net.shadowfacts.idlefactory.util.Textures
import net.shadowfacts.idlefactory.world.World

/**
 * @author shadowfacts
 */
class TileFloor(world: World, pos: Pos) : TileSingleTexture(TileFloorFactory, world, pos, Textures.FLOOR) {

	override val isReplaceable: Boolean
		get() = true

	override fun destroy() {

	}

}