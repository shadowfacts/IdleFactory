package net.shadowfacts.idlefactory.tile.impl

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import net.shadowfacts.idlefactory.nbt.impl.TagCompound
import net.shadowfacts.idlefactory.tile.Tile
import net.shadowfacts.idlefactory.tile.factory.TileTestFactory
import net.shadowfacts.idlefactory.util.Pos
import net.shadowfacts.idlefactory.util.Textures
import net.shadowfacts.idlefactory.util.Tickable
import net.shadowfacts.idlefactory.util.TooltipProvider
import net.shadowfacts.idlefactory.world.World

/**
 * @author shadowfacts
 */
class TileTest(world: World, pos: Pos) : Tile(TileTestFactory, world, pos, Textures.LOGO, 0f), Tickable, TooltipProvider {

	private var age = 0

	override fun draw(batch: SpriteBatch) {
		batch.draw(texture, pos.renderX, pos.renderY, 0f, 0f, 21f, 21f, 1f, 1f, 0f, 0, 0, 460, 460, false, false)
	}

	override fun tick() {
//		age++
//		if (age % 10 == 0) {
//			println("Age: $age")
//		}
//		if (age == 200) {
//			println("removing TileTest")
//			world.set(pos, TileFloor(world, pos))
//		}
	}

	override fun serializeNBT(tag: TagCompound): TagCompound {
		super.serializeNBT(tag)
		tag["age"] = age
		return tag
	}

	override fun deserializeNBT(tag: TagCompound) {
		super.deserializeNBT(tag)
		this.age = tag.getInt("age")
	}

	override fun getTooltip(): List<String> {
		return listOf("Age: $age", "Line 2", "blaaaaaaah")
	}

}
