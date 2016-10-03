package net.shadowfacts.idlefactory.gui

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import net.shadowfacts.idlefactory.IdleFactory
import net.shadowfacts.idlefactory.gui.widget.Widget
import net.shadowfacts.idlefactory.player.tool.ToolSelector
import net.shadowfacts.idlefactory.player.tool.plan.ToolPlanning
import net.shadowfacts.idlefactory.scene.GameScene

/**
 * @author shadowfacts
 */
class GUIIngame : GUI {

	constructor() {
		widgets.add(ToolSelectorWidget())
	}

	override fun draw(batch: SpriteBatch, shapeRenderer: ShapeRenderer) {
		super.draw(batch, shapeRenderer)

		val tool = GameScene.player!!.tools.getSelectedTool()
		tool.drawOverlay(GameScene.world!!, batch, shapeRenderer)

		ToolSelector.drawSelectedTile()
		ToolPlanning.drawPlans()

		batch.begin()
		IdleFactory.font!!.draw(batch, Gdx.graphics.framesPerSecond.toString(), 0f, Gdx.graphics.height.toFloat())
		IdleFactory.font!!.draw(batch, GameScene.player!!.money.toString(), 0f, Gdx.graphics.height.toFloat() - 15f)
		batch.end()
	}

	override fun handleKeyPress(keyCode: Int): Boolean {
		if (keyCode == Input.Keys.I) {
			GameScene.gui = GUIInventory(GameScene.player!!.inventory)
			return true
		}
		return super.handleKeyPress(keyCode)
	}

	override fun canClose(): Boolean {
		return false
	}

	class ToolSelectorWidget : Widget(0f, 0f, GameScene.player!!.tools.getToolCount() * 25f, 25f) {

		override fun draw(batch: SpriteBatch, shapeRenderer: ShapeRenderer) {
			val toolCount = GameScene.player!!.tools.getToolCount()

			shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)
			shapeRenderer.color = Color(0.2f, 0.2f, 0.2f, 1f)
			shapeRenderer.rect(0f, 0f, toolCount * 25f, 25f)
			shapeRenderer.end()

			batch.begin()
			for (i in 0.until(toolCount)) {
				val texture = GameScene.player!!.tools[i].texture
				batch.draw(texture, i * 25f, 0f, 0f, 0f, 25f, 25f, 1f, 1f, 0f, 0, 0, texture.width, texture.height, false, false)
			}
			batch.end()

			shapeRenderer.begin(ShapeRenderer.ShapeType.Line)
			val selected = GameScene.player!!.tools.selectedTool
			shapeRenderer.color = Color.WHITE
			shapeRenderer.rect(selected * 25f, 0f, 25f, 25f)
			shapeRenderer.end()
		}

		override fun handleKeyPress(keyCode: Int): Boolean {
			if (keyCode >= Input.Keys.NUM_1 && keyCode <= Input.Keys.NUM_9) {
				return GameScene.player!!.tools.onNumberKeyPressed(keyCode)
			}
			return false
		}

	}

}