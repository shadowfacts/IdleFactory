package net.shadowfacts.idlefactory.gui.widget

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import net.shadowfacts.idlefactory.IdleFactory

/**
 * @author shadowfacts
 */
class WidgetButton(x: Float, y: Float, width: Float, height: Float, var layout: GlyphLayout) : Widget(x, y, width, height) {

	var clickHandler: ((WidgetButton) -> Boolean)? = null

	constructor(x: Float, y: Float, layout: GlyphLayout) : this(x, y, layout.width, layout.height, layout)

	constructor(x: Float, y: Float, width: Float, height: Float, str: String) : this(x, y, width, height, GlyphLayout(IdleFactory.font!!, str))

	constructor(x: Float, y: Float, str: String) : this(x, y, GlyphLayout(IdleFactory.font!!, str))

	override fun draw(batch: SpriteBatch, shapeRenderer: ShapeRenderer) {
//		TODO: proper background
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)
		shapeRenderer.color = Color.RED
		shapeRenderer.rect(x, y, width, height)
		shapeRenderer.end()

		val x = x + (width / 2) - (layout.width / 2)
		val y = y + (height / 2) - (layout.height / 2)
		IdleFactory.font!!.draw(batch, layout, x, y)
	}

	override fun handleClick(screenX: Int, screenY: Int, button: Int): Boolean {
		if (clickHandler != null &&
				screenX >= x && screenX <= x + width &&
				screenY >= y && screenY <= y + height) {
			return clickHandler!!(this)
		}
		return false
	}

}