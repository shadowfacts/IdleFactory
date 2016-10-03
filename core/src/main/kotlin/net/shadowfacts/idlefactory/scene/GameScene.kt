package net.shadowfacts.idlefactory.scene

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Matrix4
import net.shadowfacts.idlefactory.gui.GUI
import net.shadowfacts.idlefactory.gui.GUIIngame
import net.shadowfacts.idlefactory.input.GameInputProcessor
import net.shadowfacts.idlefactory.nbt.TagHelper
import net.shadowfacts.idlefactory.nbt.impl.TagCompound
import net.shadowfacts.idlefactory.player.Player
import net.shadowfacts.idlefactory.tile.Tile
import net.shadowfacts.idlefactory.world.World
import java.io.File

/**
 * @author shadowfacts
 */
object GameScene : Scene() {

	var player: Player? = null
	var world: World? = null

	private var accumulator: Double = 0.0
	private val step = 1f / 20f

	var gui: GUI? = null
	var ingameGui: GUIIngame? = null

	var camera: OrthographicCamera? = null

	var batch: SpriteBatch? = null
	var shapeRenderer: ShapeRenderer? = null

	var guiBatch: SpriteBatch? = null
	var guiShapeRenderer: ShapeRenderer? = null
	val guiMatrix = Matrix4()

	override fun create() {
		player = Player()
		val playerFile = File("world/player.nbt")
		if (playerFile.exists()) {
			player!!.deserializeNBT(TagHelper.readFile(playerFile))
		} else {
			val tag = player!!.serializeNBT(TagCompound("player"))
			TagHelper.writeFile(tag, playerFile)
		}

		val worldFile = File("world/world.nbt")
		if (worldFile.exists()) {
			world = World(TagHelper.readFile(worldFile))
		} else {
			world = World(Gdx.files.internal("world_template.txt").readString())
			val tag = world!!.serializeNBT(TagCompound("world"))
			TagHelper.writeFile(tag, worldFile)
		}

		ingameGui = GUIIngame()

		camera = OrthographicCamera(world!!.getXSize() * 21f, (world!!.getYSize() * 21f) * (Gdx.graphics.height / Gdx.graphics.width))
		camera!!.position.set(camera!!.viewportWidth / 2f, camera!!.viewportHeight / 2f, 0f)
		camera!!.update()

		batch = SpriteBatch()
		shapeRenderer = ShapeRenderer()

		guiBatch = SpriteBatch()
		guiShapeRenderer = ShapeRenderer()
	}

	override fun open() {
		Gdx.input.inputProcessor = GameInputProcessor
	}

	override fun render(delta: Float) {
		handleCameraMovement()
		camera!!.update()
		batch!!.projectionMatrix = camera!!.combined

		tick(delta)

		batch!!.begin()

		world!!.draw(batch!!)

		batch!!.end()


		guiMatrix.setToOrtho2D(0f, 0f, Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())
		guiShapeRenderer!!.projectionMatrix = guiMatrix
		guiBatch!!.projectionMatrix = guiMatrix

		if (gui == null) {
			ingameGui!!.draw(guiBatch!!, guiShapeRenderer!!)
		} else {
			Gdx.gl.glEnable(GL20.GL_BLEND)
			Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA)
			guiShapeRenderer!!.begin(ShapeRenderer.ShapeType.Filled)
			guiShapeRenderer!!.color = Color(0.2f, 0.2f, 0.2f, 0.7f)
			guiShapeRenderer!!.rect(0f, 0f, Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())
			guiShapeRenderer!!.end()
			Gdx.gl.glDisable(GL20.GL_BLEND)
			gui!!.draw(guiBatch!!, guiShapeRenderer!!)
		}
	}

	private fun tick(delta: Float) {
		@Suppress("NAME_SHADOWING")
		var delta = delta
		if (delta > 0.25) delta = 0.25f

		accumulator += delta

		while (accumulator >= step) {
			world!!.tick()

			accumulator -= step
		}
	}

	fun handleCameraMovement() {
		if (gui != null) return

//		zoom
		if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
			camera!!.zoom -= 0.01f
		}
		if (Gdx.input.isKeyPressed(Input.Keys.E)) {
			camera!!.zoom += 0.01f
		}
		camera!!.zoom = MathUtils.clamp(camera!!.zoom, 0.1f, 1f)

		val effectiveViewportWidth = camera!!.viewportWidth * camera!!.zoom
		val effectiveViewportHeight = camera!!.viewportHeight * camera!!.zoom

//		translation
		val movementFactor = 10 * camera!!.zoom
		if (Gdx.input.isKeyPressed(Input.Keys.W)) {
			camera!!.translate(0f, movementFactor)
		}
		if (Gdx.input.isKeyPressed(Input.Keys.S)) {
			camera!!.translate(0f, -movementFactor)
		}
		if (Gdx.input.isKeyPressed(Input.Keys.A)) {
			camera!!.translate(-movementFactor, 0f)
		}
		if (Gdx.input.isKeyPressed(Input.Keys.D)) {
			camera!!.translate(movementFactor, 0f)
		}

		camera!!.position.x = MathUtils.clamp(camera!!.position.x, effectiveViewportWidth / 2f, (world!!.getXSize() * 21f) - effectiveViewportWidth / 2f)
		camera!!.position.y = MathUtils.clamp(camera!!.position.y, effectiveViewportHeight / 2f, (world!!.getYSize() * 21f) - effectiveViewportHeight / 2f)
	}

	override fun resize(width: Int, height: Int) {
		camera!!.viewportWidth = world!!.getXSize() * 21f
		camera!!.viewportHeight = world!!.getYSize() * 21f * height / width
		camera!!.update()
	}

	override fun dispose() {
		batch!!.dispose()
		shapeRenderer!!.dispose()

		guiBatch!!.dispose()
		guiShapeRenderer!!.dispose()
	}

	fun getHoveringTile(): Tile? {
		val pos = world!!.getPosAtScreenCoords(Gdx.input.x, Gdx.input.y)
		if (world!!.isWithinBorder(pos)) {
			return world!!.get(pos)
		} else {
			return null
		}
	}

	fun save() {
		TagHelper.writeFile(player!!.serializeNBT(TagCompound("player")), File("world/player.nbt"))
		TagHelper.writeFile(world!!.serializeNBT(TagCompound("world")), File("world/world.nbt"))
	}

}