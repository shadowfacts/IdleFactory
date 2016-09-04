package net.shadowfacts.idlefactory.input

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.InputProcessor
import net.shadowfacts.idlefactory.scene.GameScene
import net.shadowfacts.idlefactory.tile.impl.TileTest

/**
 * @author shadowfacts
 */
object GameInputProcessor : InputProcessor {

	override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
		return false
	}

	override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
		val flippedY = Gdx.graphics.height - screenY
		if (GameScene.gui != null) {
			GameScene.gui!!.handleClick(screenX, flippedY, button)
			return true
		}
		if (GameScene.ingameGui!!.handleClick(screenX, flippedY, button)) {
			return true
		}
		if (button == Input.Buttons.LEFT) {
			val selectedTool = GameScene.player!!.tools.getSelectedTool()
			selectedTool.onLeftClick(GameScene.world!!, screenX,  screenY)
		} else if (button == Input.Buttons.RIGHT) {
			val selectedTool = GameScene.player!!.tools.getSelectedTool()
			selectedTool.onRightClick(GameScene.world!!, screenX,  screenY)
		}

//		Debug
		if (Gdx.input.isButtonPressed(Input.Buttons.MIDDLE)) {
			val tile = GameScene.getHoveringTile()
			if (tile != null) {
				GameScene.world!!.set(tile.pos, TileTest(GameScene.world!!, tile.pos))
				return true
			}
		}
		return false
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

	override fun keyDown(keycode: Int): Boolean {
		return false
	}

	override fun keyUp(keycode: Int): Boolean {
		if (GameScene.gui != null) {
			GameScene.gui!!.handleKeyPress(keycode)
			return true
		}
		if (GameScene.ingameGui!!.handleKeyPress(keycode)) {
			return true
		}
		if (keycode == Input.Keys.X) {
			GameScene.save()
			System.exit(0)
		}
		return false
	}

	override fun keyTyped(character: Char): Boolean {
		return false
	}

}