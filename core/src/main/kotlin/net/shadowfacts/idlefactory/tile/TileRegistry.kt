package net.shadowfacts.idlefactory.tile

import com.google.common.collect.BiMap
import com.google.common.collect.HashBiMap

/**
 * @author shadowfacts
 */
object TileRegistry {

	private val nameTileMap: BiMap<String, TileFactory> = HashBiMap.create()
	private val nameIdMap: BiMap<String, Short> = HashBiMap.create()

	fun register(name: String, tile: TileFactory, id: Short) {
		nameTileMap.put(name, tile)
		nameIdMap.put(name, id)
	}

	operator fun get(name: String): TileFactory? {
		return nameTileMap[name]
	}

	operator fun get(id: Short): TileFactory? {
		return this[getName(id)]
	}

	fun getName(tile: TileFactory): String {
		return nameTileMap.inverse()[tile]!!
	}

	fun getName(id: Short): String {
		return nameIdMap.inverse()[id]!!
	}

	fun getId(tile: TileFactory): Short {
		return getId(getName(tile))
	}

	fun getId(name: String): Short {
		return nameIdMap[name]!!
	}

}