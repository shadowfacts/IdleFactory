package net.shadowfacts.idlefactory.nbt.impl

import net.shadowfacts.idlefactory.nbt.Tag
import net.shadowfacts.idlefactory.nbt.TagHelper
import net.shadowfacts.idlefactory.nbt.TagRegistry
import java.io.DataInputStream
import java.io.DataOutputStream
import java.util.*

/**
 * @author shadowfacts
 */
class TagList(name: String, var clazz: Class<out Tag>, var value: MutableList<Tag>) : Tag(name), Iterable<Tag> {

	val size: Int
		get() = value.size

	constructor(name: String, value: MutableList<Tag>) : this(name, Tag::class.java, value) {
		value.forEach {
			if (clazz == Tag::class.java) {
				clazz = it.javaClass
			} else if (clazz != it.javaClass) {
				throw IllegalArgumentException("All tags must be of the same type.")
			}
		}
	}

	constructor(name: String) : this(name, ArrayList())

	override fun get(): MutableList<Tag> {
		return value
	}

	override fun write(output: DataOutputStream) {
		val id = TagRegistry.getIdFor(clazz)
		if (id == -1) throw RuntimeException("TagList contains unregistered tag class $clazz")
		output.writeByte(id)

		output.writeInt(value.size)
		value.forEach {
			TagHelper.writeTag(output, it)
		}
	}

	override fun read(input: DataInputStream) {
		val id = input.readUnsignedByte()
		clazz = TagRegistry.getClassFor(id) ?: Tag::class.java
		val size = input.readInt()
		value = ArrayList(size)

		for (i in 0.until(size)) {
			value.add(TagHelper.readTag(input)!!)
		}
	}

	override fun clone(): Tag {
		return TagList(name, clazz, value)
	}

	operator fun get(index: Int): Tag {
		return value[index]
	}

	operator fun set(index: Int, value: Tag) {
		this.value[index] = value
	}

	fun add(value: Tag) {
		if (clazz == Tag::class.java) {
			clazz = value.javaClass
		} else if (clazz != value.javaClass) {
			throw RuntimeException("All tags must be of same type")
		}
		this.value.add(value)
	}

	override operator fun iterator(): Iterator<Tag> {
		return value.iterator()
	}

}