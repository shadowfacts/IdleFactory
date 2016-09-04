package net.shadowfacts.idlefactory.nbt.impl

import net.shadowfacts.idlefactory.nbt.Tag
import net.shadowfacts.idlefactory.util.readLongArray
import net.shadowfacts.idlefactory.util.writeLongArray
import java.io.DataInputStream
import java.io.DataOutputStream

/**
 * @author shadowfacts
 */
class TagLongArray(name: String, var value: LongArray) : Tag(name) {

	constructor(name: String) : this(name, LongArray(0))

	override fun get(): Any {
		return value.clone()
	}

	override fun write(output: DataOutputStream) {
		output.writeLongArray(value)
	}

	override fun read(input: DataInputStream) {
		value = input.readLongArray()
	}

	override fun clone(): Tag {
		return TagLongArray(name, value)
	}

}