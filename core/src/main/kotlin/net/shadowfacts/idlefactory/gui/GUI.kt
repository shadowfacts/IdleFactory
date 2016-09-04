package net.shadowfacts.idlefactory.gui

import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import net.shadowfacts.idlefactory.gui.widget.Widget
import net.shadowfacts.idlefactory.scene.GameScene
import java.util.*

/**
 * @author shadowfacts
 */
abstract class GUI {

	val widgets: MutableList<Widget> = ArrayList()

	open fun draw(batch: SpriteBatch, shapeRenderer: ShapeRenderer) {
		widgets.forEach {
			it.draw(batch, shapeRenderer)
		}
	}

	open fun handleClick(screenX: Int, screenY: Int, button: Int): Boolean {
		widgets.forEach {
			if (it.handleClick(screenX, screenY, button)) {
				return true
			}
		}
		return false
	}

	open fun handleKeyPress(keyCode: Int): Boolean {
		if (keyCode == Input.Keys.ESCAPE && canClose()) {
			GameScene.gui = null
			return true
		}

		widgets.forEach {
			if (it.handleKeyPress(keyCode)) {
				return true
			}
		}
		return false
	}

	open fun canClose(): Boolean {
		return true
	}

}