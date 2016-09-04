package net.shadowfacts.idlefactory.tile

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import net.shadowfacts.idlefactory.nbt.NBTSerializeable
import net.shadowfacts.idlefactory.nbt.impl.TagCompound
import net.shadowfacts.idlefactory.tile.factory.TileFactory
import net.shadowfacts.idlefactory.tile.impl.TileFloor
import net.shadowfacts.idlefactory.util.Pos
import net.shadowfacts.idlefactory.util.draw
import net.shadowfacts.idlefactory.world.World

/**
 * @author shadowfacts
 */
abstract class Tile(val factory: TileFactory, val world: World, val pos: Pos, val texture: Texture, val rotation: Float = 0f) : NBTSerializeable<TagCompound> {

	open val isReplaceable = false

	open fun draw(batch: SpriteBatch) {
		batch.draw(texture, pos.renderX, pos.renderY, rotation)
	}

	open fun destroy() {
		world.set(pos, TileFloor(world, pos))
	}

	open fun onNeighborChanged(new: Tile, old: Tile) {

	}

	override fun serializeNBT(tag: TagCompound): TagCompound {
		tag["id"] = TileRegistry.getId(factory)
		tag["pos"] = pos.toLong()
		return tag
	}

	override fun deserializeNBT(tag: TagCompound) {

	}

	companion object {

		fun deserializeNBT(tag: TagCompound, world: World): Tile {
			val id = tag.getShort("id")
			val factory = TileRegistry[id] ?: throw RuntimeException("No factory for tile ID $id")
			val pos = Pos.fromLong(tag.getLong("pos"))
			val tile = factory.createTile(world, pos)
			tile.deserializeNBT(tag)
			return tile
		}

	}

}

