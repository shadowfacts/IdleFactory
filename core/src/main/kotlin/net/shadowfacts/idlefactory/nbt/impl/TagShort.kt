package net.shadowfacts.idlefactory.nbt.impl

import net.shadowfacts.idlefactory.nbt.Tag
import java.io.DataInputStream
import java.io.DataOutputStream

/**
 * @author shadowfacts
 */
class TagShort(name: String, var value: Short) : Tag(name) {

	constructor(name: String) : this(name, 0)

	override fun get(): Any {
		return value
	}

	override fun write(output: DataOutputStream) {
		output.writeShort(value.toInt())
	}

	override fun read(input: DataInputStream) {
		value = input.readShort()
	}

	override fun clone(): Tag {
		return TagShort(name, value)
	}

}