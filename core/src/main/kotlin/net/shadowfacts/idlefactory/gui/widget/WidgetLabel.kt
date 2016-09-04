package net.shadowfacts.idlefactory.gui.widget

import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import net.shadowfacts.idlefactory.IdleFactory

/**
 * @author shadowfacts
 */
class WidgetLabel(x: Float, y: Float, layout: GlyphLayout) : Widget(x, y, layout.width, layout.height) {

	var layout: GlyphLayout = layout
		set(layout: GlyphLayout) {
			field = layout
			width = layout.width
			height = layout.height
		}

	constructor(x: Float, y: Float, str: String) : this(x, y, GlyphLayout(IdleFactory.font!!, str))

	override fun draw(batch: SpriteBatch, shapeRenderer: ShapeRenderer) {
		IdleFactory.font!!.draw(batch, layout, x, y)
	}

}