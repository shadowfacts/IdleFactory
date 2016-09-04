package net.shadowfacts.idlefactory.item

import com.google.common.collect.BiMap
import com.google.common.collect.HashBiMap

/**
 * @author shadowfacts
 */
object ItemRegistry {

	private val nameItemMap: BiMap<String, ItemDefinition> = HashBiMap.create()
	private val nameIdMap: BiMap<String, Short> = HashBiMap.create()

	fun register(name: String, item: ItemDefinition, id: Short) {
		nameItemMap.put(name, item)
		nameIdMap.put(name, id)
	}

	fun exists(name: String): Boolean {
		return nameIdMap.containsKey(name)
	}

	fun exists(id: Short): Boolean {
		return nameIdMap.inverse().containsKey(id)
	}

	operator fun get(name: String): ItemDefinition? {
		return nameItemMap[name]
	}

	operator fun get(id: Short): ItemDefinition? {
		return this[getName(id)]
	}

	fun getName(item: ItemDefinition): String {
		return nameItemMap.inverse()[item]!!
	}

	fun getName(id: Short): String {
		return nameIdMap.inverse()[id]!!
	}

	fun getId(item: ItemDefinition): Short {
		return getId(getName(item))
	}

	fun getId(name: String): Short {
		return nameIdMap[name]!!
	}

}