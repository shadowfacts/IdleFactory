package net.shadowfacts.idlefactory.scene

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Matrix4
import net.shadowfacts.idlefactory.IdleFactory
import net.shadowfacts.idlefactory.gui.GUI
import net.shadowfacts.idlefactory.gui.GUIMainMenu
import net.shadowfacts.idlefactory.input.MainMenuInputProcessor
import net.shadowfacts.idlefactory.util.Textures

/**
 * @author shadowfacts
 */
object MenuScene : Scene() {

	var matrix: Matrix4? = null
	var batch: SpriteBatch? = null
	var shapeRenderer: ShapeRenderer? = null

	var gui: GUI? = null

	override fun create() {
		matrix = Matrix4()
		batch = SpriteBatch()
		shapeRenderer = ShapeRenderer()

		gui = GUIMainMenu()
	}

	override fun open() {
		Gdx.input.inputProcessor = MainMenuInputProcessor
	}

	override fun render(delta: Float) {
		if (handleInput()) return

		matrix!!.setToOrtho2D(0f, 0f, Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())
		batch!!.projectionMatrix = matrix
		shapeRenderer!!.projectionMatrix = matrix

		batch!!.begin()

		gui!!.draw(batch!!, shapeRenderer!!)

		batch!!.end()
	}

	fun handleInput(): Boolean {
		if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
			IdleFactory.setScene(GameScene)
			return true
		}
		return false
	}

	override fun dispose() {
		batch!!.dispose()
	}

}