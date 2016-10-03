package net.shadowfacts.idlefactory.player.tool.plan

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import net.shadowfacts.idlefactory.nbt.auto.AutoSerializer
import net.shadowfacts.idlefactory.nbt.auto.Serialize
import net.shadowfacts.idlefactory.nbt.impl.TagCompound
import net.shadowfacts.idlefactory.util.Pos
import net.shadowfacts.idlefactory.world.World

/**
 * @author shadowfacts
 */
abstract class Plan(@Serialize val pos: Pos, @Serialize val id: Int) {

	abstract fun draw(batch: SpriteBatch)

	open fun serializeNBT(tag: TagCompound): TagCompound {
		AutoSerializer.serialize(tag, this)
		return tag
	}

	open fun deserializeNBT(tag: TagCompound) {
		AutoSerializer.deserialize(tag, this)
	}

	companion object {

		fun deserializeNBT(tag: TagCompound): Plan {
			val id = tag.getInt("id")
			val pos = Pos.fromLong(tag.getLong("pos"))
			val factory = ToolPlanning.planFactories[id]
			val plan = factory(pos)
			plan.deserializeNBT(tag)
			return plan
		}

	}

}