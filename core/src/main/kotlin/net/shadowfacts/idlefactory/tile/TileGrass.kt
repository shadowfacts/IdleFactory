package net.shadowfacts.idlefactory.tile

import net.shadowfacts.idlefactory.util.Pos
import net.shadowfacts.idlefactory.util.Textures
import net.shadowfacts.idlefactory.world.World

/**
 * @author shadowfacts
 */
class TileGrass(world: World, pos: Pos) : Tile(TileGrassFactory, world, pos, Textures.GRASS) {

	override val isReplaceable: Boolean
		get() = false

}