package net.shadowfacts.idlefactory.player.tool.plan

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import net.shadowfacts.idlefactory.nbt.impl.TagCompound
import net.shadowfacts.idlefactory.nbt.impl.TagList
import net.shadowfacts.idlefactory.player.tool.Tool
import net.shadowfacts.idlefactory.scene.GameScene
import net.shadowfacts.idlefactory.tile.impl.TileFloor
import net.shadowfacts.idlefactory.util.Pos
import net.shadowfacts.idlefactory.util.Textures
import net.shadowfacts.idlefactory.world.World
import java.util.*

/**
 * @author shadowfacts
 */
object ToolPlanning : Tool(Textures.TOOL_PLANNING) {

	var mode = 0

	val planFactories: Array<(Pos) -> Plan>

	var plans: Array<Array<Plan?>>
		private set

	init {
		planFactories = arrayOf(
				{ pos -> PlanWall(pos) },
				{ pos -> PlanNumber(1, pos) },
				{ pos -> PlanNumber(2, pos) },
				{ pos -> PlanNumber(3, pos) },
				{ pos -> PlanNumber(4, pos) },
				{ pos -> PlanNumber(5, pos) }
		)

//		val world = GameScene.world!!
//		TODO: initialize me when the world's initialized
		val list: MutableList<Array<Plan?>> = mutableListOf()
		for (x in 0.until(68)) {
			list.add(kotlin.arrayOfNulls(68))
		}
		plans = list.toTypedArray()
	}

	fun get(pos: Pos): Plan? {
		return get(pos.x, pos.y)
	}

	fun get(x: Int, y: Int): Plan? {
		return plans[y][x]
	}

	fun set(pos: Pos, plan: Plan?) {
		set(pos.x, pos.y, plan)
	}

	fun set(x: Int, y: Int, plan: Plan?) {
		plans[y][x] = plan
	}

	override fun onLeftClick(world: World, screenX: Int, screenY: Int) {
		val pos = world.getPosAtScreenCoords(screenX, screenY)
		if (world.isWithinBorder(pos) && world.get(pos) is TileFloor) {
			set(pos, planFactories[mode](pos))
		}
	}

	override fun onRightClick(world: World, screenX: Int, screenY: Int) {
		val pos = world.getPosAtScreenCoords(screenX, screenY)
		if (world.isWithinBorder(pos)) {
			set(pos, null)
		}
	}

	// TODO: listen for tile place and remove plan if appropriate

	override fun drawOverlay(world: World, batch: SpriteBatch, shapeRenderer: ShapeRenderer) {
		val pos = world.getPosAtScreenCoords(Gdx.input.x, Gdx.input.y)
		if (world.isWithinBorder(pos)) {
			shapeRenderer.projectionMatrix = GameScene.camera!!.combined

			shapeRenderer.begin(ShapeRenderer.ShapeType.Line)
			shapeRenderer.color = Color.BLUE
			shapeRenderer.rect(pos.renderX - 0.1f, pos.renderY - 0.1f, 21f + 0.2f, 21f + 0.2f)
			shapeRenderer.end()
		}
	}

	fun drawPlans() {
		@Suppress("NAME_SHADOWING")
		val batch = GameScene.batch!!

		batch.begin()
		plans.forEach {
			it.filter {
				it != null
			}.forEach {
				it!!.draw(batch)
			}
		}
		batch.end()
	}

	override fun serializeNBT(tag: TagCompound): TagCompound {
		val list = TagList("x")
		for (y in 0.until(68)) {
			val innerList = TagList("y")
			for (x in 0.until(68)) {
				val plan = get(x, y)
				val planTag = TagCompound("tile")
				if (plan != null) {
					plan.serializeNBT(planTag)
				} else {
					planTag["empty"] = 0.toByte()
				}
				innerList.add(planTag)
			}
			list.add(innerList)
		}
		tag["x"] = list
		return tag
	}

	override fun deserializeNBT(tag: TagCompound) {
		@Suppress("UNCHECKED_CAST")
		val tagList = tag.getTagList("x") as List<TagList>
		val list = ArrayList<Array<Plan?>>(tagList.size)
		for (x in 0.until(tagList.size)) {
			val innerTagList = tagList[x]
			val innerList = ArrayList<Plan?>(innerTagList.size)
			for (y in 0.until(innerTagList.size)) {
				val planTag = innerTagList[y] as TagCompound
				if (!planTag.hasTag("empty")) {
					innerList.add(Plan.deserializeNBT(planTag))
				} else {
					innerList.add(null)
				}
			}
			list.add(innerList.toTypedArray())
		}
		plans = list.toTypedArray()
	}


}