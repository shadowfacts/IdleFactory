package net.shadowfacts.idlefactory.tile.impl

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import net.shadowfacts.idlefactory.nbt.auto.Serialize
import net.shadowfacts.idlefactory.tile.Tile
import net.shadowfacts.idlefactory.tile.factory.TileWallFactory
import net.shadowfacts.idlefactory.util.Pos
import net.shadowfacts.idlefactory.util.Side
import net.shadowfacts.idlefactory.util.Textures
import net.shadowfacts.idlefactory.world.World

/**
 * @author shadowfacts
 */
class TileWall(world: World, pos: Pos, destroyable: Boolean) : Tile(TileWallFactory, world, pos) {

	@Serialize
	var destroyable: Boolean = destroyable
		private set

	constructor(world: World, pos: Pos) : this(world, pos, true) {

	}

	override fun draw(batch: SpriteBatch) {
		batch.draw(Textures.FLOOR, pos.renderX, pos.renderY)

		val right = connects(Side.RIGHT)
		val left = connects(Side.LEFT)
		val up = connects(Side.UP)
		val down = connects(Side.DOWN)

		var drew = false
		if (right) {
			batch.draw(Textures.WALL_HORIZONTAL, pos.renderX + 5f, pos.renderY, 16f, 21f)
			drew = true
		}
		if (left) {
			batch.draw(Textures.WALL_HORIZONTAL, pos.renderX, pos.renderY, 16f, 21f)
			drew = true
		}
		if (up && down) {
			drew = true
			batch.draw(Textures.WALL_VERTICAL, pos.renderX, pos.renderY)
		}
		if (up) {
			drew = true
			if (left || right) {
				batch.draw(Textures.WALL_VERTICAL, pos.renderX, pos.renderY + 17f, 21f, 4f)
			} else {
				batch.draw(Textures.WALL_VERTICAL, pos.renderX, pos.renderY + 10.5f, 21f, 10.5f)
			}
		}
		if (down) {
			drew = true
			if (left || right) {
				batch.draw(Textures.WALL_VERTICAL, pos.renderX, pos.renderY, 21f, 12f)
			} else {
				batch.draw(Textures.WALL_VERTICAL, pos.renderX, pos.renderY, 21f, 10.5f)
			}
		}

		if (!drew) {
			batch.draw(Textures.WALL_CENTER, pos.renderX, pos.renderY, 21f, 21f)
		}
	}

	private fun connects(side: Side): Boolean {
		val newPos = pos.offset(side)
		if (!world.isWithinBorder(newPos)) return false
		return world.get(newPos) is TileWall
	}

	override fun destroy() {
		if (destroyable) {
			super.destroy()
		}
	}

}