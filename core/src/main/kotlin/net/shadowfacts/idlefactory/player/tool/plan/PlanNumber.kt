package net.shadowfacts.idlefactory.player.tool.plan

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import net.shadowfacts.idlefactory.util.Pos
import net.shadowfacts.idlefactory.util.Textures

/**
 * @author shadowfacts
 */
class PlanNumber(number: Int, pos: Pos) : Plan(pos, number) {

	val texture = when (number) {
		1 -> Textures.PLAN_1
		2 -> Textures.PLAN_2
		3 -> Textures.PLAN_3
		4 -> Textures.PLAN_4
		5 -> Textures.PLAN_5
		else -> Textures.LOGO
	}

	override fun draw(batch: SpriteBatch) {
		batch.draw(texture, pos.renderX, pos.renderY, 21f, 21f)
	}

}