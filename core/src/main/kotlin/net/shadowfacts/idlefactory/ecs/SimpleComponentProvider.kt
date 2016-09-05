package net.shadowfacts.idlefactory.ecs

import net.shadowfacts.idlefactory.nbt.impl.TagCompound
import net.shadowfacts.idlefactory.util.Side
import net.shadowfacts.idlefactory.util.Tickable
import java.util.*

/**
 * @author shadowfacts
 */
class SimpleComponentProvider : ComponentProvider {

	private val components: MutableMap<Side, MutableMap<Class<*>, Any>> = HashMap()

	fun register(side: Side?, clazz: Class<*>, it: Any) {
		if (side != null) {
			if (!components.containsKey(side)) {
				components[side] = HashMap()
			}
			components[side]!![clazz] = it
		} else {
			Side.values().forEach { side ->
				register(side, clazz, it)
			}
		}
	}

	override fun <T> hasComponent(side: Side, clazz: Class<T>): Boolean {
		return components.containsKey(side) && components[side]!!.containsKey(clazz)
	}

	override fun <T> getComponent(side: Side, clazz: Class<T>): T? {
		if (components.containsKey(side)) {
			val map = components[side]!!
			if (map.containsKey(clazz)) {
				@Suppress("UNCHECKED_CAST")
				return map[clazz] as T
			}
		}
		return null
	}

	fun tick() {
		forEach {
			if (it is Tickable) {
				it.tick()
			}
		}
	}

	fun serializeNBT(tag: TagCompound): TagCompound {
		for ((side, map) in components) {
			val sideTag = TagCompound(side.name)

			for ((clazz, it) in map) {
				if (it is ComponentSerializeable<*>) {
					sideTag[clazz.name] = it.serializeNBT()
				}
			}

			tag[side.name] = sideTag
		}
		return tag
	}

	fun deserializeNBT(tag: TagCompound) {
		for ((side, map) in components) {
			if (tag.hasTag(side.name)) {
				val sideTag = tag.getTagCompound(side.name)!!

				for ((clazz, it) in map) {
					if (it is ComponentSerializeable<*> && sideTag.hasTag(clazz.name)) {
						it.deserializeNBT(sideTag.getTag(clazz.name)!!)
					}
				}
			}
		}
	}

	private fun forEach(action: (Any) -> Unit) {
		components.values.flatMap { it.values }.forEach(action)
	}

}