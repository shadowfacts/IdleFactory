package net.shadowfacts.idlefactory.player.tool

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import net.shadowfacts.idlefactory.item.Stack
import net.shadowfacts.idlefactory.nbt.impl.TagCompound
import net.shadowfacts.idlefactory.scene.GameScene
import net.shadowfacts.idlefactory.util.Textures
import net.shadowfacts.idlefactory.world.World

/**
 * @author shadowfacts
 */
object ToolRemove : Tool(Textures.TOOL_REMOVE) {

	override fun onLeftClick(world: World, screenX: Int, screenY: Int) {
		val pos = world.getPosAtScreenCoords(screenX, screenY)
		if (world.isWithinBorder(pos)) {
			val tile = world.get(pos)
			tile.destroy()
			val itemDef = tile.factory.getItemDefinition()
			if (itemDef != null) {
				GameScene.player!!.inventory.insert(Stack(itemDef, 1))
			}
		}
	}

	override fun onRightClick(world: World, screenX: Int, screenY: Int) {

	}

	override fun drawOverlay(world: World, batch: SpriteBatch, shapeRenderer: ShapeRenderer) {
		val pos = world.getPosAtScreenCoords(Gdx.input.x, Gdx.input.y)
		if (world.isWithinBorder(pos)) {
			shapeRenderer.projectionMatrix = GameScene.camera!!.combined

			shapeRenderer.begin(ShapeRenderer.ShapeType.Line)
			shapeRenderer.color = Color.RED
			shapeRenderer.rect(pos.renderX - 0.1f, pos.renderY - 0.1f, 21f + 0.2f, 21f + 0.2f)
			shapeRenderer.end()
		}
	}

	override fun serializeNBT(tag: TagCompound): TagCompound {
		return tag
	}

	override fun deserializeNBT(tag: TagCompound) {

	}

}