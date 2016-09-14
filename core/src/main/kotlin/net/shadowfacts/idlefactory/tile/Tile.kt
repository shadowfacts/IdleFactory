package net.shadowfacts.idlefactory.tile

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import net.shadowfacts.idlefactory.ecs.ComponentProvider
import net.shadowfacts.idlefactory.ecs.SimpleComponentProvider
import net.shadowfacts.idlefactory.nbt.NBTSerializable
import net.shadowfacts.idlefactory.nbt.auto.AutoSerializer
import net.shadowfacts.idlefactory.nbt.auto.Serialize
import net.shadowfacts.idlefactory.nbt.impl.TagCompound
import net.shadowfacts.idlefactory.tile.factory.TileFactory
import net.shadowfacts.idlefactory.tile.impl.TileFloor
import net.shadowfacts.idlefactory.util.Pos
import net.shadowfacts.idlefactory.util.Side
import net.shadowfacts.idlefactory.util.draw
import net.shadowfacts.idlefactory.world.World

/**
 * @author shadowfacts
 */
abstract class Tile(val factory: TileFactory, val world: World, @Serialize val pos: Pos, val texture: Texture, val rotation: Float = 0f) : NBTSerializable<TagCompound>, ComponentProvider {

	protected val components = SimpleComponentProvider()

	open val isReplaceable = false

	open fun initComponents() {

	}

	open fun draw(batch: SpriteBatch) {
		batch.draw(texture, pos.renderX, pos.renderY, rotation)
	}

	open fun destroy() {
		world.set(pos, TileFloor(world, pos))
	}

	open fun onNeighborChanged(new: Tile, old: Tile) {

	}

	override fun serializeNBT(tag: TagCompound): TagCompound {
		AutoSerializer.serialize(tag, this)
		tag["id"] = TileRegistry.getId(factory)
		tag["components"] = components.serializeNBT(TagCompound("components"))
		return tag
	}

	override fun deserializeNBT(tag: TagCompound) {
		AutoSerializer.deserialize(tag, this)
		components.deserializeNBT(tag.getTagCompound("components")!!)
	}

	fun tickComponents() {
		components.tick()
	}

	override fun <T> hasComponent(side: Side, clazz: Class<T>): Boolean {
		return components.hasComponent(side, clazz)
	}

	override fun <T> getComponent(side: Side, clazz: Class<T>): T? {
		return components.getComponent(side, clazz)
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

