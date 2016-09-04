package net.shadowfacts.idlefactory.gui.widget

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Rectangle

/**
 * @author shadowfacts
 */
abstract class Widget(var x: Float, var y: Float, var width: Float, var height: Float) {

	abstract fun draw(batch: SpriteBatch, shapeRenderer: ShapeRenderer)

	open fun handleClick(screenX: Int, screenY: Int, button: Int): Boolean {
		return false
	}

	open fun handleKeyPress(keyCode: Int): Boolean {
		return false
	}

	fun centerIn(rect: Rectangle) {
		x = rect.x + (rect.width + width) / 2
		y = rect.y + (rect.height + height) / 2
	}

}