package net.shadowfacts.idlefactory.player.tool

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import net.shadowfacts.idlefactory.item.ItemTile
import net.shadowfacts.idlefactory.nbt.impl.TagCompound
import net.shadowfacts.idlefactory.scene.GameScene
import net.shadowfacts.idlefactory.util.Textures
import net.shadowfacts.idlefactory.world.World

/**
 * @author shadowfacts
 */
object ToolPlace : Tool(Textures.TOOL_PLACE) {

	var selectedSlot = -1

	override fun onLeftClick(world: World, screenX: Int, screenY: Int) {
		if (selectedSlot != -1) {
			val pos = world.getPosAtScreenCoords(screenX, screenY)
			if (world.isWithinBorder(pos)) {
				val item = GameScene.player!!.inventory[selectedSlot]
				if (item != null && item.type is ItemTile) {

					val current = world.get(pos)
					if (current.isReplaceable) {
						val tile = item.type.tile.createTile(world, pos)
						world.set(pos, tile)
						GameScene.player!!.inventory[selectedSlot] = item.decrementStackSize(1)
					}

				}
			}
		}
	}

	override fun onRightClick(world: World, screenX: Int, screenY: Int) {

	}

	override fun drawOverlay(world: World, batch: SpriteBatch, shapeRenderer: ShapeRenderer) {
		@Suppress("NAME_SHADOWING")
		val batch = GameScene.batch!!

		if (selectedSlot != -1) {
			val pos = world.getPosAtScreenCoords(Gdx.input.x, Gdx.input.y)
			if (world.isWithinBorder(pos)) {
				val item = GameScene.player!!.inventory[selectedSlot]
				if (item != null && item.type is ItemTile) {

					batch.begin()
					batch.color = Color(1f, 1f, 1f, 0.75f)
					batch.draw(item.type.texture, pos.renderX, pos.renderY, 0f, 0f, 21f, 21f, 1f, 1f, 0f, 0, 0, item.type.texture.width, item.type.texture.height, false, false)
					batch.color = Color(1f, 1f, 1f, 1f)
					batch.end()

				}
			}
		}
	}

	override fun serializeNBT(tag: TagCompound): TagCompound {
		tag["placeSlot"] = ToolPlace.selectedSlot
		return tag
	}

	override fun deserializeNBT(tag: TagCompound) {
		ToolPlace.selectedSlot = tag.getInt("placeSlot")
	}

}