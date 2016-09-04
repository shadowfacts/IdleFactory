package net.shadowfacts.idlefactory.util

import com.badlogic.gdx.graphics.Texture

/**
 * @author shadowfacts
 */
object Textures {

	val LOGO by lazy { Texture("textures/logo.png") }

	val FLOOR by lazy { Texture("textures/floor.png") }
	val WALL_CENTER by lazy { Texture("textures/wall/center.png") }
	val WALL_HORIZONTAL by lazy { Texture("textures/wall/horizontal.png") }
	val WALL_VERTICAL by lazy { Texture("textures/wall/vertical.png") }

	val IRON_ORE by lazy { Texture("textures/iron/ore.png") }
	val IRON_INGOT by lazy { Texture("textures/iron/ingot.png") }

	val TOOL_SELECTOR by lazy { Texture("textures/tool/selector.png") }
	val TOOL_PLACE by lazy { Texture("textures/tool/place.png") }
	val TOOL_REMOVE by lazy { Texture("textures/tool/remove.png") }

	val GRASS by lazy { Texture("textures/grass.png") }

	fun dispose() {
		LOGO.dispose()

		FLOOR.dispose()
		WALL_CENTER.dispose()
		WALL_HORIZONTAL.dispose()
		WALL_VERTICAL.dispose()

		IRON_ORE.dispose()
		IRON_INGOT.dispose()
	}

}