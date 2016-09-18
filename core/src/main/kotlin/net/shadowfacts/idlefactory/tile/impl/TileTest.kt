package net.shadowfacts.idlefactory.tile.impl

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import net.shadowfacts.idlefactory.inventory.Inventory
import net.shadowfacts.idlefactory.inventory.SimpleInventory
import net.shadowfacts.idlefactory.item.Stack
import net.shadowfacts.idlefactory.nbt.auto.Serialize
import net.shadowfacts.idlefactory.nbt.impl.TagCompound
import net.shadowfacts.idlefactory.nbt.impl.TagList
import net.shadowfacts.idlefactory.tile.TileSingleTexture
import net.shadowfacts.idlefactory.tile.factory.ItemTileTest
import net.shadowfacts.idlefactory.tile.factory.TileTestFactory
import net.shadowfacts.idlefactory.util.*
import net.shadowfacts.idlefactory.world.World

/**
 * @author shadowfacts
 */
class TileTest(world: World, pos: Pos) : TileSingleTexture(TileTestFactory, world, pos, Textures.LOGO), Tickable, TooltipProvider {

	@Serialize
	private var age = 0
	private var inv = SimpleInventory(1)

	init {
		inv[0] = Stack(ItemTileTest, 3)
	}

	override fun initComponents() {
		components.register(null, Inventory::class.java, inv)
	}

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
		val list = TagList("inv")
		tag["inv"] = list
		inv.serializeNBT(list)
		return tag
	}

	override fun deserializeNBT(tag: TagCompound) {
		super.deserializeNBT(tag)
		inv.deserializeNBT(tag.getTag("inv")!!)
	}

	override fun getTooltip(): List<String> {
		return listOf("Age: $age", "Has: ${hasComponent(Side.UP, Inventory::class.java)}", "Inv: ${getComponent(Side.UP, Inventory::class.java)?.get(0)?.type?.name}")
	}

}
