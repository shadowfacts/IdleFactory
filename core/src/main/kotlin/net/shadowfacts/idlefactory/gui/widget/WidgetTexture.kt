package net.shadowfacts.idlefactory.gui.widget

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer

/**
 * @author shadowfacts
 */
class WidgetTexture(x: Float, y: Float, val texture: Texture, width: Float, height: Float) : Widget(x, y, width, height) {

	constructor(x: Float, y: Float, texture: Texture) : this(x, y, texture, texture.width.toFloat(), texture.height.toFloat())

	override fun draw(batch: SpriteBatch, shapeRenderer: ShapeRenderer) {
		batch.draw(texture, x, y, width, height)
	}

}