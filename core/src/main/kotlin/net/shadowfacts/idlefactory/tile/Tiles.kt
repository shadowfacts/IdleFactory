package net.shadowfacts.idlefactory.tile

import net.shadowfacts.idlefactory.item.ItemDefinition
import net.shadowfacts.idlefactory.item.ItemTile
import net.shadowfacts.idlefactory.util.Textures

/**
 * @author shadowfacts
 */
object TileTestFactory : TileFactory("test", Textures.LOGO, TileTest::class.java) {
	override fun register() {
		TileRegistry.register(name, this, 0)
	}
}

object TileWallFactory : TileFactory("wall", Textures.WALL_CENTER, TileWall::class.java) {
	override fun register() {
		TileRegistry.register(name, this, 1)
	}
}

object TileFloorFactory : TileFactory("floor", Textures.FLOOR, TileFloor::class.java) {
	override fun register() {
		TileRegistry.register(name, this, 2)
	}
}

object ItemTileTest : ItemTile(TileTestFactory) {
}

object ItemTileWall : ItemTile(TileWallFactory) {
}

fun initTiles() {
	arrayOf(TileTestFactory, TileWallFactory, TileFloorFactory).forEach(TileFactory::register)

	arrayOf(ItemTileTest, ItemTileWall).forEach(ItemDefinition::register)
}