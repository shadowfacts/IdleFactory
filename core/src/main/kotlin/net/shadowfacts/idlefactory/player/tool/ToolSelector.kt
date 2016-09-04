package net.shadowfacts.idlefactory.player.tool

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector3
import net.shadowfacts.idlefactory.IdleFactory
import net.shadowfacts.idlefactory.nbt.impl.TagCompound
import net.shadowfacts.idlefactory.scene.GameScene
import net.shadowfacts.idlefactory.tile.Tile
import net.shadowfacts.idlefactory.util.Pos
import net.shadowfacts.idlefactory.util.Textures
import net.shadowfacts.idlefactory.util.TooltipProvider
import net.shadowfacts.idlefactory.world.World

/**
 * @author shadowfacts
 */
object ToolSelector : Tool(Textures.TOOL_SELECTOR) {

	var selectedTile: Pos? = null

	override fun onLeftClick(world: World, screenX: Int, screenY: Int) {
		selectedTile = world.getPosAtScreenCoords(screenX, screenY)
	}

	override fun onRightClick(world: World, screenX: Int, screenY: Int) {
		selectedTile = null
	}

	override fun drawOverlay(world: World, batch: SpriteBatch, shapeRenderer: ShapeRenderer) {
		val pos = world.getPosAtScreenCoords(Gdx.input.x, Gdx.input.y)
		if (world.isWithinBorder(pos)) {
			shapeRenderer.projectionMatrix = GameScene.camera!!.combined

			shapeRenderer.begin(ShapeRenderer.ShapeType.Line)
			shapeRenderer.color = Color.GREEN
			shapeRenderer.rect(pos.renderX - 0.1f, pos.renderY - 0.1f, 21f + 0.2f, 21f + 0.2f)
			shapeRenderer.end()
		}
	}

	fun drawSelectedTile() {
		val selectedTile = selectedTile ?: return

		val shapeRenderer = GameScene.shapeRenderer!!
		shapeRenderer.projectionMatrix = GameScene.camera!!.combined

		shapeRenderer.begin(ShapeRenderer.ShapeType.Line)
		shapeRenderer.color = Color(0f, 1f, 1f, 1f)
		shapeRenderer.rect(selectedTile.renderX - 0.1f, selectedTile.renderY - 0.1f, 21f + 0.2f, 21f + 0.2f)
		shapeRenderer.end()



		val tile = GameScene.world!!.get(selectedTile)

		if (tile is TooltipProvider) {
			val font = IdleFactory.font!!
			val guiBatch = GameScene.guiBatch!!
			val guiShapeRenderer = GameScene.guiShapeRenderer!!

			val tooltip = tile.getTooltip().reversed().map { GlyphLayout(font, it) }

			if (tooltip.size == 0) return

			val totalHeight = tooltip.map { it.height + 5 }.sum() + 5
			val maxWidth = tooltip.map { it.width }.max()!! + 10

			val unprojected = GameScene.camera!!.project(Vector3(selectedTile.renderX + 21f, selectedTile.renderY + 10.5f, 0f))

			val x = unprojected.x + 5
			var y = unprojected.y - (totalHeight / 2)

			Gdx.gl.glEnable(GL20.GL_BLEND)
			Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA)
			guiShapeRenderer.begin(ShapeRenderer.ShapeType.Filled)
			guiShapeRenderer.color = Color(0f, 0f, 0f, 0.75f)
			guiShapeRenderer.rect(x, y, maxWidth, totalHeight)
			guiShapeRenderer.end()
			Gdx.gl.glDisable(GL20.GL_BLEND)

			guiBatch.begin()


			tooltip.forEachIndexed { i, it ->
				y += it.height + 5
				font.draw(guiBatch, it, x + 5, y)
			}

			guiBatch.end()

		}
	}

	override fun serializeNBT(tag: TagCompound): TagCompound {
		if (selectedTile != null) {
			tag["selectedTile"] = selectedTile!!.toLong()
		}
		return tag
	}

	override fun deserializeNBT(tag: TagCompound) {
		if (tag.hasTag("selectedTile")) {
			selectedTile = Pos.fromLong(tag.getLong("selectedTile"))
		}
	}

}
