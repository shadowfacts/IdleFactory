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

	val WALL_PLAN_CENTER by lazy { Texture("textures/plan/wall/center.png") }
	val WALL_PLAN_HORIZONTAL by lazy { Texture("textures/plan/wall/horizontal.png") }
	val WALL_PLAN_VERTICAL by lazy { Texture("textures/plan/wall/vertical.png") }
	val PLAN_1 by lazy { Texture("textures/plan/one.png") }
	val PLAN_2 by lazy { Texture("textures/plan/two.png") }
	val PLAN_3 by lazy { Texture("textures/plan/three.png") }
	val PLAN_4 by lazy { Texture("textures/plan/four.png") }
	val PLAN_5 by lazy { Texture("textures/plan/five.png") }

	val IRON_ORE by lazy { Texture("textures/iron/ore.png") }
	val IRON_INGOT by lazy { Texture("textures/iron/ingot.png") }

	val TOOL_SELECTOR by lazy { Texture("textures/tool/selector.png") }
	val TOOL_PLACE by lazy { Texture("textures/tool/place.png") }
	val TOOL_REMOVE by lazy { Texture("textures/tool/remove.png") }
	val TOOL_PLANNING = LOGO // FIXME

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