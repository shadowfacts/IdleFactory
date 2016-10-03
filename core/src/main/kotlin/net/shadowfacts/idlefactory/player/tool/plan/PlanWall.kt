package net.shadowfacts.idlefactory.player.tool.plan

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import net.shadowfacts.idlefactory.scene.GameScene
import net.shadowfacts.idlefactory.util.Pos
import net.shadowfacts.idlefactory.util.Side
import net.shadowfacts.idlefactory.util.Textures

/**
 * @author shadowfacts
 */
class PlanWall(pos: Pos) : Plan(pos, 0) {

	override fun draw(batch: SpriteBatch) {
		val right = connects(Side.RIGHT)
		val left = connects(Side.LEFT)
		val up = connects(Side.UP)
		val down = connects(Side.DOWN)

		var drew = false
		if (right) {
			batch.draw(Textures.WALL_PLAN_HORIZONTAL, pos.renderX + 5f, pos.renderY, 16f, 21f)
			drew = true
		}
		if (left) {
			batch.draw(Textures.WALL_PLAN_HORIZONTAL, pos.renderX, pos.renderY, 16f, 21f)
			drew = true
		}
		if (up && down) {
			drew = true
			batch.draw(Textures.WALL_PLAN_VERTICAL, pos.renderX, pos.renderY)
		}
		if (up) {
			drew = true
			if (left || right) {
				batch.draw(Textures.WALL_PLAN_VERTICAL, pos.renderX, pos.renderY + 17f, 21f, 4f)
			} else {
				batch.draw(Textures.WALL_PLAN_VERTICAL, pos.renderX, pos.renderY + 10.5f, 21f, 10.5f)
			}
		}
		if (down) {
			drew = true
			if (left || right) {
				batch.draw(Textures.WALL_PLAN_VERTICAL, pos.renderX, pos.renderY, 21f, 12f)
			} else {
				batch.draw(Textures.WALL_PLAN_VERTICAL, pos.renderX, pos.renderY, 21f, 10.5f)
			}
		}

		if (!drew) {
			batch.draw(Textures.WALL_PLAN_CENTER, pos.renderX, pos.renderY, 21f, 21f)
		}
	}

	private fun connects(side: Side): Boolean {
		val newPos = pos.offset(side)
		if (!GameScene.world!!.isWithinBorder(newPos)) return false
		return ToolPlanning.get(newPos) is PlanWall
	}

}