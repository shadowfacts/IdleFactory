package net.shadowfacts.idlefactory.nbt.impl

import net.shadowfacts.idlefactory.nbt.Tag
import java.io.DataInputStream
import java.io.DataOutputStream

/**
 * @author shadowfacts
 */
class TagLong(name: String, var value: Long) : Tag(name) {

	constructor(name: String) : this(name, 0)

	override fun get(): Any {
		return value
	}

	override fun write(output: DataOutputStream) {
		output.writeLong(value)
	}

	override fun read(input: DataInputStream) {
		value = input.readLong()
	}

	override fun clone(): Tag {
		return TagLong(name, value)
	}

}