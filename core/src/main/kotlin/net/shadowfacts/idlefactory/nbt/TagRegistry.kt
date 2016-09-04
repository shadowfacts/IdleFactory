package net.shadowfacts.idlefactory.nbt

import com.google.common.collect.BiMap
import com.google.common.collect.HashBiMap
import net.shadowfacts.idlefactory.nbt.impl.*

/**
 * @author shadowfacts
 */
object TagRegistry {

	private val map: BiMap<Int, Class<out Tag>> = HashBiMap.create()

	init {
		register(1, TagByte::class.java)
		register(2, TagShort::class.java)
		register(3, TagInt::class.java)
		register(4, TagLong::class.java)
		register(5, TagFloat::class.java)
		register(6, TagDouble::class.java)
		register(7, TagByteArray::class.java)
		register(8, TagString::class.java)
		register(9, TagList::class.java)
		register(10, TagCompound::class.java)
		register(11, TagIntArray::class.java)

		register(60, TagDoubleArray::class.java)
		register(61, TagFloatArray::class.java)
		register(62, TagLongArray::class.java)
		register(63, TagShortArray::class.java)
		register(64, TagStringArray::class.java)
	}

	fun register(id: Int, clazz: Class<out Tag>) {
		if (map.containsKey(id)) {
			throw IllegalArgumentException("ID $id is already registered to ${map[id]}")
		}

		map.put(id, clazz)
	}

	fun getClassFor(id: Int): Class<out Tag>? {
		return map[id]
	}

	fun getIdFor(clazz: Class<out Tag>): Int {
		val inverse = map.inverse()
		if (!inverse.containsKey(clazz)) return -1
		else return map.inverse()[clazz]!!
	}

	fun createInstance(id: Int, name: String): Tag {
		val clazz = getClassFor(id) ?: throw IllegalArgumentException("Invalid id $id, unable to create tag instance")

		val ctor = clazz.getConstructor(String::class.java)
		ctor.isAccessible = true
		return ctor.newInstance(name)
	}

}