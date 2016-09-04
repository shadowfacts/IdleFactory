package net.shadowfacts.idlefactory.nbt.impl

import net.shadowfacts.idlefactory.nbt.Tag
import java.io.DataInputStream
import java.io.DataOutputStream

/**
 * @author shadowfacts
 */
class TagByte(name: String, var value: Byte) : Tag(name) {

	constructor(name: String) : this(name, 0)

	override fun get(): Any {
		return value
	}

	override fun write(output: DataOutputStream) {
		output.writeByte(value.toInt())
	}

	override fun read(input: DataInputStream) {
		value = input.readByte()
	}

	override fun clone(): TagByte {
		return TagByte(name, value)
	}

}