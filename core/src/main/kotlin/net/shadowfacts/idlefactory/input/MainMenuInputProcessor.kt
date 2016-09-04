package net.shadowfacts.idlefactory.input

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.InputProcessor
import net.shadowfacts.idlefactory.IdleFactory
import net.shadowfacts.idlefactory.scene.GameScene
import net.shadowfacts.idlefactory.scene.MenuScene

/**
 * @author shadowfacts
 */
object MainMenuInputProcessor : InputProcessor {

	override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
		return false
	}

	override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
		val flippedY = Gdx.graphics.height - screenY
		return MenuScene.gui!!.handleClick(screenX, flippedY, button)
	}

	override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean {
		return false
	}

	override fun mouseMoved(screenX: Int, screenY: Int): Boolean {
		return false
	}

	override fun scrolled(amount: Int): Boolean {
		return false
	}

	override fun keyUp(keycode: Int): Boolean {
		if (keycode == Input.Keys.ENTER) {
			IdleFactory.setScene(GameScene)
			return true
		}
		return MenuScene.gui!!.handleKeyPress(keycode)
	}

	override fun keyDown(keycode: Int): Boolean {
		return false
	}

	override fun keyTyped(character: Char): Boolean {
		return false
	}

}