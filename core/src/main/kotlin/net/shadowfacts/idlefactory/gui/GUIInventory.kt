package net.shadowfacts.idlefactory.gui

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import net.shadowfacts.idlefactory.IdleFactory
import net.shadowfacts.idlefactory.gui.widget.Widget
import net.shadowfacts.idlefactory.player.PlayerInventory
import net.shadowfacts.idlefactory.player.tool.ToolPlace
import net.shadowfacts.idlefactory.item.Stack

/**
 * @author shadowfacts
 */
class GUIInventory : GUI {

	private val inv: PlayerInventory
	private var heldStack: Stack? = null

	constructor(inv: PlayerInventory) {
		this.inv = inv

		val invWidth = 9 * 31
		val invHeight = 4 * 31

		val baseX = (Gdx.graphics.width - invWidth) / 2
		val baseY = (Gdx.graphics.height - invHeight) / 2

		for (i in 0.until(inv.getSize())) {
			widgets.add(WidgetSlot(this, i, inv, baseX + ((i % 9) * 31f), baseY + ((i / 9) * 31f)))
		}
	}

	override fun draw(batch: SpriteBatch, shapeRenderer: ShapeRenderer) {
		val invWidth = (9 * 31) + 2
		val invHeight = (4 * 31) + 2

		val baseX = (Gdx.graphics.width - invWidth) / 2
		val baseY = (Gdx.graphics.height - invHeight) / 2

		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)
		shapeRenderer.color = Color(0.75f, 0.75f, 0.75f, 1f)
		shapeRenderer.rect(baseX.toFloat(), baseY.toFloat(), invWidth.toFloat(), invHeight.toFloat())
		shapeRenderer.end()

		super.draw(batch, shapeRenderer)

		if (heldStack != null) {
			val x = Gdx.input.x - 12.5f
			val y = (Gdx.graphics.height - Gdx.input.y) - 12.5f

			val texture = heldStack!!.type.texture
			batch.begin()
			batch.draw(texture, x, y, 0f, 0f, 25f, 25f, 1f, 1f, 0f, 0, 0, texture.width, texture.height, false, false)
			if (heldStack!!.amount != 1) {
				val layout = GlyphLayout(IdleFactory.font!!, heldStack!!.amount.toString())
				IdleFactory.font!!.draw(batch, heldStack!!.amount.toString(), x + 25f - layout.width, y + 25f - layout.height)
			}
			batch.end()
		}
	}

	override fun canClose(): Boolean {
		return heldStack == null
	}

	class WidgetSlot(val gui: GUIInventory, val slot: Int, val inv: PlayerInventory, x: Float, y: Float) : Widget(x, y, 31f, 31f) {

		override fun draw(batch: SpriteBatch, shapeRenderer: ShapeRenderer) {
			shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)
			shapeRenderer.color = Color(0.4f, 0.4f, 0.4f, 1f)
			shapeRenderer.rect(x + 1f, y + 1f, 29f, 29f)
			shapeRenderer.end()

			val stack = inv[slot]
			if (stack != null) {

				val texture = stack.type.texture
				batch.begin()
				batch.draw(texture, x + 3f, y + 3f, 0f, 0f, 25f, 25f, 1f, 1f, 0f, 0, 0, texture.width, texture.height, false, false)
				if (stack.amount != 1) {
					val layout = GlyphLayout(IdleFactory.font!!, stack.amount.toString())
					IdleFactory.font!!.draw(batch, stack.amount.toString(), x.toFloat() + 28f - layout.width, y.toFloat() + 28f - layout.height)
				}
				batch.end()
			}

			if (ToolPlace.selectedSlot == slot) {
				shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)
				shapeRenderer.color = Color.YELLOW
				shapeRenderer.circle(x + width - 9f, y + height - 9f, 3f)
				shapeRenderer.end()
			}
		}

		override fun handleClick(screenX: Int, screenY: Int, button: Int): Boolean {
			if (screenX >= x && screenX <= x + width &&
					screenY >= y && screenY <= y + height) {

				if (button == Input.Buttons.LEFT) {
					if (gui.heldStack != null) {
						gui.heldStack = inv.insert(slot, gui.heldStack!!, false)
					} else {
						gui.heldStack = inv.extract(slot, 100, false)
					}
					return true
				} else if (button == Input.Buttons.RIGHT) {
					if (gui.heldStack != null) {
						val result = inv.insert(slot, Stack(gui.heldStack!!.type, 1), true)
						if (result == null) { // success
							inv.insert(slot, Stack(gui.heldStack!!.type, 1), false)
							gui.heldStack = gui.heldStack!!.decrementStackSize(1)
						}
					} else {
						val current = inv[slot]
						if (current != null) {
							gui.heldStack = inv.extract(slot, current.amount / 2, false)
						}
					}
				} else if (button == Input.Buttons.MIDDLE) {
					if (ToolPlace.selectedSlot == slot) {
						ToolPlace.selectedSlot = -1
					} else {
						ToolPlace.selectedSlot = slot
					}
				}

			}
			return false
		}

	}

}
