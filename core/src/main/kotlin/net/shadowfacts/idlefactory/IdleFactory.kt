package net.shadowfacts.idlefactory

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import net.shadowfacts.idlefactory.item.initItems
import net.shadowfacts.idlefactory.scene.GameScene
import net.shadowfacts.idlefactory.scene.MenuScene
import net.shadowfacts.idlefactory.scene.Scene
import net.shadowfacts.idlefactory.tile.initTiles
import net.shadowfacts.idlefactory.util.Textures

object IdleFactory : ApplicationAdapter() {

	val scenes: Array<Scene> = arrayOf(MenuScene, GameScene)
	private var scene: Scene = MenuScene

	var batch: SpriteBatch? = null

	var fontTex: Texture? = null
	var font: BitmapFont? = null

	override fun create() {
		initTiles()
		initItems()

		font = BitmapFont()
		batch = SpriteBatch()

		scenes.forEach(Scene::create)
		scene.open()

//		fontTex = Texture(Gdx.files.internal("ArialDF.png"))
//		fontTex!!.setFilter(Texture.TextureFilter.MipMapLinearNearest, Texture.TextureFilter.Linear)
//		font = BitmapFont(Gdx.files.internal("ArialDF.fnt"), TextureRegion(fontTex!!), false)
	}

	override fun render() {
//		if (Gdx.input.isKeyPressed(Input.Keys.X)) {
//			System.exit(0)
//		}

		Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

		scene.render(Gdx.graphics.deltaTime)

		batch!!.begin()
		font!!.draw(batch!!, Gdx.graphics.framesPerSecond.toString(), 0f, Gdx.graphics.height.toFloat())
		batch!!.end()
	}

	override fun resize(width: Int, height: Int) {
		scenes.forEach {
			it.resize(width, height)
		}
	}

	override fun dispose () {
		scenes.forEach(Scene::dispose)
		batch!!.dispose()
		font!!.dispose()
		Textures.dispose()
	}

	fun setScene(scene: Scene) {
		this.scene.close()
		this.scene = scene
		scene.open()
	}

}
